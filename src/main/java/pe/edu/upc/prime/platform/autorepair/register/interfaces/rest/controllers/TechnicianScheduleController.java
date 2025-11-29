package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetAllTechnicianSchedulesQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetSchedulesByTechnicianIdQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetTechnicianScheduleByIdQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianScheduleCommandService;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianScheduleQueryService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianRepository;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.assemblers.TechnicianScheduleAssembler;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.CreateTechnicianScheduleRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.UpdateTechnicianScheduleRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.TechnicianScheduleResponse;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST Controller for managing Technician Schedules.
 */
@RestController
@RequestMapping("/api/v1/technician_schedules")
public class TechnicianScheduleController {

    private final TechnicianScheduleCommandService technicianScheduleCommandService;
    private final TechnicianScheduleQueryService technicianScheduleQueryService;
    private final TechnicianRepository technicianRepository;

    public TechnicianScheduleController(TechnicianScheduleCommandService technicianScheduleCommandService,
                                        TechnicianScheduleQueryService technicianScheduleQueryService,
                                        TechnicianRepository technicianRepository) {
        this.technicianScheduleCommandService = technicianScheduleCommandService;
        this.technicianScheduleQueryService = technicianScheduleQueryService;
        this.technicianRepository = technicianRepository;
    }

    // --------------------------------------------------------
    // CREATE
    // --------------------------------------------------------
    @Operation(summary = "Create a new Technician Schedule")
    @ApiResponse(responseCode = "201", description = "Technician schedule created successfully")
    @PostMapping
    public ResponseEntity<TechnicianScheduleResponse> createSchedule(@RequestBody CreateTechnicianScheduleRequest request) {
        // Validate Technician existence
        Optional<Technician> technicianOpt = technicianRepository.findById(request.idTechnician());
        if (technicianOpt.isEmpty()) {
            throw new NotFoundIdException(Technician.class, request.idTechnician());
        }

        // Create command and handle
        CreateTechnicianScheduleCommand command =
                TechnicianScheduleAssembler.toCommandFromRequest(request, technicianOpt.get());
        String scheduleId = technicianScheduleCommandService.handle(command);

        // Retrieve persisted entity for response
        var schedule = technicianScheduleQueryService
                .handle(new GetTechnicianScheduleByIdQuery(scheduleId))
                .orElseThrow(() -> new NotFoundIdException(TechnicianSchedule.class, scheduleId));

        var response = TechnicianScheduleAssembler.toResponseFromEntity(schedule);
        return ResponseEntity.status(201).body(response);
    }

    // --------------------------------------------------------
    // GET ALL
    // --------------------------------------------------------
    @Operation(summary = "Get all Technician Schedules")
    @ApiResponse(responseCode = "200", description = "List of schedules retrieved successfully")
    @GetMapping
    public ResponseEntity<List<TechnicianScheduleResponse>> getAllSchedules() {
        List<TechnicianSchedule> schedules = technicianScheduleQueryService.handle(new GetAllTechnicianSchedulesQuery());
        var responses = schedules.stream()
                .map(TechnicianScheduleAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // --------------------------------------------------------
    // GET BY ID
    // --------------------------------------------------------
    @Operation(summary = "Get Technician Schedule by ID")
    @ApiResponse(responseCode = "200", description = "Schedule retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Schedule not found")
    @GetMapping("/{scheduleId}")
    public ResponseEntity<TechnicianScheduleResponse> getScheduleById(@PathVariable String scheduleId) {
        Optional<TechnicianSchedule> scheduleOpt =
                technicianScheduleQueryService.handle(new GetTechnicianScheduleByIdQuery(scheduleId));

        if (scheduleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var response = TechnicianScheduleAssembler.toResponseFromEntity(scheduleOpt.get());
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // GET BY TECHNICIAN ID
    // --------------------------------------------------------
    @Operation(summary = "Get all schedules for a specific Technician")
    @ApiResponse(responseCode = "200", description = "Schedules retrieved successfully")
    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<TechnicianScheduleResponse>> getSchedulesByTechnicianId(@PathVariable String technicianId) {
        List<TechnicianSchedule> schedules =
                technicianScheduleQueryService.handle(new GetSchedulesByTechnicianIdQuery(technicianId));

        var responses = schedules.stream()
                .map(TechnicianScheduleAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // --------------------------------------------------------
    // UPDATE
    // --------------------------------------------------------
    @Operation(summary = "Update an existing Technician Schedule")
    @ApiResponse(responseCode = "200", description = "Schedule updated successfully")
    @PutMapping("/{scheduleId}")
    public ResponseEntity<TechnicianScheduleResponse> updateSchedule(@PathVariable String scheduleId,
                                                                     @RequestBody UpdateTechnicianScheduleRequest request) {
        UpdateTechnicianScheduleCommand command =
                TechnicianScheduleAssembler.toCommandFromRequest(scheduleId, request);

        Optional<TechnicianSchedule> updatedScheduleOpt = technicianScheduleCommandService.handle(command);

        if (updatedScheduleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var response = TechnicianScheduleAssembler.toResponseFromEntity(updatedScheduleOpt.get());
        return ResponseEntity.ok(response);
    }

    // --------------------------------------------------------
    // DELETE
    // --------------------------------------------------------
    @Operation(summary = "Delete Technician Schedule by ID")
    @ApiResponse(responseCode = "204", description = "Schedule deleted successfully")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String scheduleId) {
        technicianScheduleCommandService.handle(new DeleteTechnicianScheduleCommand(scheduleId));
        return ResponseEntity.noContent().build();
    }
}
