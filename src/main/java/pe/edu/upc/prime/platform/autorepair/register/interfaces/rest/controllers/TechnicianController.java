package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing Technicians
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/v1/technicians")
@Tag(name = "Technicians", description = "Technician Management Endpoints")
public class TechnicianController {
    /**
     * Service for handling Technician commands
     */
    private final TechnicianCommandService technicianCommandService;
    /**
     * Service for handling Technician queries
     */
    private final TechnicianQueryService technicianQueryService;

    /**
     * Constructor for TechnicianController
     *
     * @param technicianCommandService the technician command service
     * @param technicianQueryService  the technician query service
     */
    public TechnicianController(TechnicianCommandService technicianCommandService,
                                TechnicianQueryService technicianQueryService) {
        this.technicianCommandService = technicianCommandService;
        this.technicianQueryService = technicianQueryService;
    }

    /**
     * Create a new Technician
     *
     * @param request The request body containing technician data
     * @return ResponseEntity with the created technician data
     */
    @Operation(summary = "Create a new technician",
            description = "Creates a new technician with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Technician data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateTechnicianRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Technician created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TechnicianResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<TechnicianResponse> createTechnician(@RequestBody CreateTechnicianRequest request) {
        var createTechnicianCommand = TechnicianAssembler.toCommandFromRequest(request);
        var technicianId = this.technicianCommandService.handle(createTechnicianCommand);

        if (Objects.isNull(technicianId) || technicianId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getTechnicianQuery = new GetTechnicianByIdQuery(technicianId);
        var technician = this.technicianQueryService.handle(getTechnicianQuery);
        if (technician.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var technicianResponse = TechnicianAssembler.toResponseFromEntity(technician.get());
        return new ResponseEntity<>(technicianResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all technicians
     *
     * @return ResponseEntity with the list of all technicians
     */
    @Operation(summary = "Retrieve all technicians",
            description = "Retrieves a list of all technicians",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Technicians retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TechnicianResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<TechnicianResponse>> getAllTechnicians() {
        List<Technician> technicians = technicianQueryService.handle(new GetAllTechniciansQuery());
        var responses = technicians.stream()
                .map(TechnicianAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieve a technician by its ID
     *
     * @param technician_id The unique ID of the technician
     * @return ResponseEntity with the technician data
     */
    @Operation(summary = "Retrieve a technician by its ID",
            description = "Retrieves a technician using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Technician retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TechnicianResponse.class))),
            })
    @GetMapping("/{technician_id}")
    public ResponseEntity<TechnicianResponse> getTechnicianById(@PathVariable Long technician_id) {
        var technicianOpt = technicianQueryService.handle(new GetTechnicianByIdQuery(technician_id));
        if (technicianOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var response = TechnicianAssembler.toResponseFromEntity(technicianOpt.get());
        return ResponseEntity.ok(response);
    }


    /**
     * Update an existing Technician
     *
     * @param technician_id The unique ID of the technician to update
     * @param request     The request body containing updated technician data
     * @return ResponseEntity with the updated technician data
     */
    @Operation(summary = "Update an existing technician",
            description = "Update an existing technician with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Technician data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateTechnicianRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Technician updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TechnicianResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{technician_id}")
    public ResponseEntity<TechnicianResponse> updateTechnician(@PathVariable Long technician_id,
                                                               @RequestBody UpdateTechnicianRequest request) {
        var updateTechnicianCommand = TechnicianAssembler.toCommandFromRequest(technician_id, request);
        var optionalTechnician = technicianCommandService.handle(updateTechnicianCommand);
        if (optionalTechnician.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var technicianResponse = TechnicianAssembler.toResponseFromEntity(optionalTechnician.get());
        return ResponseEntity.ok(technicianResponse);
    }

    /**
     * Delete a technician by its ID
     *
     * @param technician_id The unique ID of the technician
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete a technician by its ID",
            description = "Deletes a technician using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Technician deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid technician ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{technician_id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long technician_id) {
        technicianCommandService.handle(new DeleteTechnicianCommand(technician_id));
        return ResponseEntity.noContent().build();
    }
}

