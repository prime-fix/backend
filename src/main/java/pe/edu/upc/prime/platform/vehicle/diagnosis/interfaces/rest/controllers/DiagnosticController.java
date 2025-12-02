package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
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
@RequestMapping(value = "/api/v1/diagnostics", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Diagnostics", description = "Diagnostics Management Endpoints")
public class DiagnosticController {
    /**
     * The diagnostic query service for handling diagnosis-related queries.
     */
    private final DiagnosticQueryService diagnosticQueryService;

    /**
     * The diagnostic command service for handling diagnosis-related commands.
     */
    private final DiagnosticCommandService diagnosticCommandService;

    /**
     * Constructor for VehiclesDiagnosisController.
     *
     * @param diagnosticQueryService   the service for handling diagnosis queries
     * @param diagnosticCommandService the service for handling diagnosis commands
     */
    public DiagnosticController(
            DiagnosticQueryService diagnosticQueryService,
            DiagnosticCommandService diagnosticCommandService) {
        this.diagnosticQueryService = diagnosticQueryService;
        this.diagnosticCommandService = diagnosticCommandService;
    }


    /**
     * Create a new diagnostic.
     *
     * @param request the request body containing diagnostic data
     * @return the created DiagnosticResponse
     */
    @Operation(summary = "Create a new diagnostic",
            description = "Creates a new diagnostic with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Diagnostic data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateDiagnosticRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Diagnostic created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DiagnosticResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<DiagnosticResponse> createDiagnostic(@RequestBody CreateDiagnosticRequest request) {

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
     * Retrieve all diagnostics, optionally filtered by vehicle ID.
     *
     * @param vehicleId the vehicle ID to filter diagnostics (optional)
     * @return a list of DiagnosticResponse objects
     */
    @Operation(summary = "Retrieve all diagnostics",
            description = "Retrieves all diagnostics or filters them by vehicle id if provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Diagnostics retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DiagnosticResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<DiagnosticResponse>> getAllDiagnostics(@RequestParam(required = false) Long vehicleId) {
        List<Diagnostic> diagnostics;

        if (Objects.isNull(vehicleId)) {
            var getAllDiagnosticsQuery = new GetAllDiagnosticsQuery();
            diagnostics = diagnosticQueryService.handle(getAllDiagnosticsQuery);
        } else {
            var getDiagnosticsByVehicleIdQuery = new GetDiagnosticsByVehicleIdQuery(new VehicleId(vehicleId));
            diagnostics = diagnosticQueryService.handle(getDiagnosticsByVehicleIdQuery);
        }

        var diagnosticResponses = diagnostics.stream()
                .map(DiagnosticAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(diagnosticResponses);
    }

    @Operation(summary = "Retrieve a diagnostic by its ID",
            description = "Retrieves a diagnostic using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Diagnostic retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DiagnosticResponse.class))),
            })
    @GetMapping("/{diagnostic_id}")
    public ResponseEntity<DiagnosticResponse> getDiagnosticById(@PathVariable Long diagnostic_id) {
        var getDiagnosticByIdQuery = new GetDiagnosticByIdQuery(diagnostic_id);
        var optionalDiagnostic = this.diagnosticQueryService.handle(getDiagnosticByIdQuery);

        if (optionalDiagnostic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var diagnosticResponse = DiagnosticAssembler.toResourceFromEntity(optionalDiagnostic.get());
        return ResponseEntity.ok(diagnosticResponse);
    }

    @Operation(summary = "Update an existing diagnostic",
            description = "Update an existing diagnostic with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Diagnostic data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateDiagnosticRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Diagnostic updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DiagnosticResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{diagnostic_id}")
    public ResponseEntity<DiagnosticResponse> updateDiagnostic(
            @PathVariable Long diagnostic_id,
            @RequestBody UpdateDiagnosticRequest request) {

        var updateDiagnosticCommand = DiagnosticAssembler.toCommandFromRequest(diagnostic_id, request);
        var optionalDiagnostic = this.diagnosticCommandService.handle(updateDiagnosticCommand);

        if (optionalDiagnostic.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var diagnosticResponse  = DiagnosticAssembler.toResourceFromEntity(optionalDiagnostic.get());
        return ResponseEntity.ok(diagnosticResponse);
    }


    @Operation(summary = "Delete a diagnostic by its ID",
            description = "Deletes a diagnostic using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Diagnostic deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid diagnostic ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{diagnostic_id}")
    public ResponseEntity<?> deleteDiagnostic(@PathVariable Long diagnostic_id) {
        var deleteDiagnosticCommand = new DeleteDiagnosticCommand(diagnostic_id);
        this.diagnosticCommandService.handle(deleteDiagnosticCommand);
        return ResponseEntity.noContent().build();
    }
}