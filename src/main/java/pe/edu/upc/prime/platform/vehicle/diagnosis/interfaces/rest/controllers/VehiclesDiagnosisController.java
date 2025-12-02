package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.ServiceUnavailableResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllDiagnosticsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticByIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.DiagnosticCommandService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.DiagnosticQueryService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers.DiagnosticAssembler;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.CreateDiagnosticRequest;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.DiagnosticResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.UpdateDiagnosticRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing VehiclesDiagnosis.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/diagnosis", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "VehiclesDiagnosis", description = "Vehicles Diagnosis Management Endpoints")
public class VehiclesDiagnosisController {

    private final DiagnosticQueryService diagnosticQueryService;
    private final DiagnosticCommandService diagnosticCommandService;

    /**
     * Constructor for VehiclesDiagnosisController.
     *
     * @param diagnosticQueryService   the service for handling diagnosis queries
     * @param diagnosticCommandService the service for handling diagnosis commands
     */
    public VehiclesDiagnosisController(
            DiagnosticQueryService diagnosticQueryService,
            DiagnosticCommandService diagnosticCommandService) {
        this.diagnosticQueryService = diagnosticQueryService;
        this.diagnosticCommandService = diagnosticCommandService;
    }

    /**
     * Endpoint to create a new diagnostic.
     *
     * @param request the diagnostic data to be created
     * @return a ResponseEntity containing the created diagnostic resource or a bad request status
     *     if creation fails
     */
    @Operation(summary = "Create a new diagnostic",
            description = "Creates a new diagnostic with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Diagnostic data for creation", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateDiagnosticRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diagnostic created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiagnosticResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PostMapping
    public ResponseEntity<DiagnosticResponse> createDiagnostic(@Valid @RequestBody CreateDiagnosticRequest request) {

        var createProfileCommand = DiagnosticAssembler.toCommandFromRequest(request);
        var diagnosticId = this.diagnosticCommandService.handle(createProfileCommand);

        if (Objects.isNull(diagnosticId) || diagnosticId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getDiagnosticByIdQuery = new GetDiagnosticByIdQuery(diagnosticId);
        var optionalDiagnostic = this.diagnosticQueryService.handle(getDiagnosticByIdQuery);

        if (optionalDiagnostic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var diagnosticResponse = DiagnosticAssembler.toResourceFromEntity(optionalDiagnostic.get());
        return new ResponseEntity<>(diagnosticResponse, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all diagnostic or filter by vehicle id.
     *
     * @param vehicleId optional vehicle id parameter to filter diagnostics
     * @return a list of ResponseMinimalEntity
     */
    @Operation( summary = "Retrieve a diagnostic by vehicleId",
            description = "Retrieves all diagnostic or filters by vehicleId if provided"
    )
    @GetMapping("/{vehicleId}")
    public ResponseEntity<List<DiagnosticResponse>> getAllDiagnosticsByVehicleId(@PathVariable Long vehicleId) {

        if (vehicleId == null || vehicleId.equals(0L)) {
            return ResponseEntity.ok(List.of());
        }

        var query = new GetDiagnosticsByVehicleIdQuery(new VehicleId(vehicleId));
        var diagnostics = diagnosticQueryService.handle(query);

        var resources = diagnostics.stream()
                .map(DiagnosticAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Endpoint to retrieve all diagnostics
     *
     * @return a list of DiagnosticResponse
     */
    @Operation( summary = "Retrieve all diagnostic",
            description = "Retrieves all diagnostic"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostics retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DiagnosticResponse.class)) ))
    })
    @GetMapping
    public ResponseEntity<List<DiagnosticResponse>> getAllDiagnostic() {
        var diagnostics = diagnosticQueryService.handle(new GetAllDiagnosticsQuery());
        var responses = diagnostics.stream()
                .map(DiagnosticAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Endpoint to update an existing diagnostic.
     *
     * @param diagnosticId the ID of the diagnostic to be updated
     * @param request  the updated diagnostic data
     * @return a ResponseEntity containing the updated diagnostic resource or a bad request status
     *     if the update fails
     */
    @Operation(summary = "Update an existing diagnostic",
            description = "Update an existing diagnostic with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Diagnostic data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateDiagnosticRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiagnosticResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PutMapping("/{diagnosticId}")
    public ResponseEntity<DiagnosticResponse> updateDiagnostic(
            @PathVariable Long diagnosticId,
            @Valid @RequestBody UpdateDiagnosticRequest request) {

        var updateDiagnosticCommand = DiagnosticAssembler.toCommandFromRequest(diagnosticId, request);
        var optionalDiagnostic = this.diagnosticCommandService.handle(updateDiagnosticCommand);

        if (optionalDiagnostic.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var diagnosticResponse  = DiagnosticAssembler.toResourceFromEntity(optionalDiagnostic.get());
        return ResponseEntity.ok(diagnosticResponse);
    }

    /**
     * Endpoint to delete a diagnostic by its ID.
     *
     * @param diagnostic_id the ID of the diagnostic to be deleted
     * @return a ResponseEntity with no content if deletion is successful
     */
    @Operation(summary = "Delete a diagnostic by its ID",
            description = "Deletes a diagnostic using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Diagnostic deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class))),
    })
    @DeleteMapping("/{diagnostic_id}")
    public ResponseEntity<?> deleteDiagnostic(@PathVariable Long diagnostic_id) {
        var deleteDiagnosticCommand = new DeleteDiagnosticCommand(diagnostic_id);
        this.diagnosticCommandService.handle(deleteDiagnosticCommand);
        return ResponseEntity.noContent().build();
    }
}