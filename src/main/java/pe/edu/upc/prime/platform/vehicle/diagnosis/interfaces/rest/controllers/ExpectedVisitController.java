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
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllExpectedVisitsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetExpectedVisitByIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.ExpectedVisitCommandService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.ExpectedVisitQueryService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers.ExpectedVisitAssembler;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.CreateExpectedVisitRequest;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.ExpectedVisitResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.UpdateExpectedVisitRequest;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/expected_visits", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Expected Visits", description = "Expected Visit Management Endpoints")
public class ExpectedVisitController {
    private final ExpectedVisitQueryService expectedVisitQueryService;

    private final ExpectedVisitCommandService expectedVisitCommandService;

    public ExpectedVisitController(ExpectedVisitQueryService expectedVisitQueryService,
                                   ExpectedVisitCommandService expectedVisitCommandService) {
        this.expectedVisitQueryService = expectedVisitQueryService;
        this.expectedVisitCommandService = expectedVisitCommandService;
    }

    @Operation(summary = "Create a new expected visit",
            description = "Creates a new expected visit with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Expected Visit data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateExpectedVisitRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Expected Visit created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExpectedVisitResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<ExpectedVisitResponse> createExpectedVisit(@RequestBody CreateExpectedVisitRequest request) {
        var createExpectedVisitCommand = ExpectedVisitAssembler.toCommandFromRequest(request);
        var expectedVisitId = this.expectedVisitCommandService.handle(createExpectedVisitCommand);

        if (Objects.isNull(expectedVisitId) || expectedVisitId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getExpectedVisitByIdQuery = new GetExpectedVisitByIdQuery(expectedVisitId);
        var expectedVisit = this.expectedVisitQueryService.handle(getExpectedVisitByIdQuery);
        if (expectedVisit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var expectedVisitResponse = ExpectedVisitAssembler.toResourceFromEntity(expectedVisit.get());
        return new ResponseEntity<>(expectedVisitResponse, HttpStatus.CREATED);
    }

     @Operation(summary = "Retrieve all expected visits",
            description = "Retrieves a list of all expected visits or filters them by vehicle id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expected Visits retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExpectedVisitResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<ExpectedVisitResponse>> getAllExpectedVisits() {
        var getAllExpectedVisitsQuery = new GetAllExpectedVisitsQuery();
        var expectedVisits = this.expectedVisitQueryService.handle(getAllExpectedVisitsQuery);

        var expectedVisitResponses = expectedVisits.stream()
                .map(ExpectedVisitAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(expectedVisitResponses);
    }

    @Operation(summary = "Retrieve a expected visit by its ID",
            description = "Retrieves a expected visit using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expected Visit retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExpectedVisitResponse.class))),
            })
    @GetMapping("/{expected_visit_id}")
    public ResponseEntity<ExpectedVisitResponse> getExpectedVisitById(@PathVariable Long expected_visit_id) {
        var getExpectedVisitByIdQuery = new GetExpectedVisitByIdQuery(expected_visit_id);
        var optionalExpectedVisit = this.expectedVisitQueryService.handle(getExpectedVisitByIdQuery);
        if (optionalExpectedVisit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var expectedVisitResponse = ExpectedVisitAssembler.toResourceFromEntity(optionalExpectedVisit.get());
        return ResponseEntity.ok(expectedVisitResponse);
    }

    @Operation(summary = "Update an existing expected visit",
            description = "Update an existing expected visit with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Expected Visit data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateExpectedVisitRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expected Visit updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExpectedVisitResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{expected_visit_id}")
    public ResponseEntity<ExpectedVisitResponse> updateExpectedVisit(@PathVariable Long expected_visit_id,
                                                                     @RequestBody UpdateExpectedVisitRequest request) {
        var updateExpectedVisitCommand = ExpectedVisitAssembler.toCommandFromRequest(expected_visit_id, request);
        var optionalExpectedVisit = this.expectedVisitCommandService.handle(updateExpectedVisitCommand);

        if (optionalExpectedVisit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var expectedVisitResponse = ExpectedVisitAssembler.toResourceFromEntity(optionalExpectedVisit.get());
        return ResponseEntity.ok(expectedVisitResponse);
    }

    @Operation(summary = "Delete a expected visit by its ID",
            description = "Deletes a expected visit using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Expected Visit deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid expected visit ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{expected_visit_id}")
    public ResponseEntity<?> deleteExpectedVisit(@PathVariable Long expected_visit_id) {
        var deleteExpectedVisitCommand = new DeleteExpectedVisitCommand(expected_visit_id);
        this.expectedVisitCommandService.handle(deleteExpectedVisitCommand);
        return ResponseEntity.noContent().build();
    }
}
