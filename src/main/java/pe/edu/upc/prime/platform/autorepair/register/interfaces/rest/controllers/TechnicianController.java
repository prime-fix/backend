package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetAllTechniciansQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetTechnicianByIdQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianCommandService;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianQueryService;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.assemblers.TechnicianAssembler;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.CreateTechnicianRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.TechnicianResponse;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.UpdateTechnicianRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/technicians")
@Tag(name = "Technicians", description = "Technician Management Endpoints")
public class TechnicianController {

    private final TechnicianCommandService technicianCommandService;
    private final TechnicianQueryService technicianQueryService;

    public TechnicianController(TechnicianCommandService technicianCommandService,
                                TechnicianQueryService technicianQueryService) {
        this.technicianCommandService = technicianCommandService;
        this.technicianQueryService = technicianQueryService;
    }

    // CREATE
    @Operation(summary = "Create a new Technician")
    @ApiResponse(responseCode = "201", description = "Technician created successfully")
    @PostMapping
    public ResponseEntity<TechnicianResponse> createTechnician(@RequestBody CreateTechnicianRequest request) {
        var command = TechnicianAssembler.toCommandFromRequest(request);
        var technician = technicianCommandService.handle(command);
        var response = TechnicianAssembler.toResponseFromEntity(technician);
        return ResponseEntity.status(201).body(response);
    }

    // GET ALL
    @Operation(summary = "Get all Technicians")
    @ApiResponse(responseCode = "200", description = "List of Technicians retrieved successfully")
    @GetMapping
    public ResponseEntity<List<TechnicianResponse>> getAllTechnicians() {
        List<Technician> technicians = technicianQueryService.handle(new GetAllTechniciansQuery());
        var responses = technicians.stream()
                .map(TechnicianAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // GET BY ID
    @Operation(summary = "Get Technician by ID")
    @ApiResponse(responseCode = "200", description = "Technician retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Technician not found")
    @GetMapping("/{technician_id}")
    public ResponseEntity<TechnicianResponse> getTechnicianById(@PathVariable Long technician_id) {
        var technicianOpt = technicianQueryService.handle(new GetTechnicianByIdQuery(technician_id));
        if (technicianOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var response = TechnicianAssembler.toResponseFromEntity(technicianOpt.get());
        return ResponseEntity.ok(response);
    }

    // UPDATE
    @Operation(summary = "Update an existing Technician")
    @ApiResponse(responseCode = "200", description = "Technician updated successfully")
    @PutMapping("/{technician_id}")
    public ResponseEntity<TechnicianResponse> updateTechnician(@PathVariable Long technician_id,
                                                               @RequestBody UpdateTechnicianRequest request) {
        var command = TechnicianAssembler.toCommandFromRequest(technician_id, request);
        var updatedTechnician = technicianCommandService.handle(command);
        var response = TechnicianAssembler.toResponseFromEntity(updatedTechnician);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @Operation(summary = "Delete Technician by ID")
    @ApiResponse(responseCode = "204", description = "Technician deleted successfully")
    @DeleteMapping("/{technician_id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long technician_id) {
        technicianCommandService.handle(new DeleteTechnicianCommand(technician_id));
        return ResponseEntity.noContent().build();
    }
}

