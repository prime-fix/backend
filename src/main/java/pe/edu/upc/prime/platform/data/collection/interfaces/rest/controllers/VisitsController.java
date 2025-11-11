package pe.edu.upc.prime.platform.data.collection.interfaces.rest.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.*;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitCommandService;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitQueryService;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers.VisitAssembler;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateVisitRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.NotFoundResponse;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for managing visits.
 */
@CrossOrigin(origins="*", methods ={ RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
@RestController
@RequestMapping(value = "/api/v1/visits", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Visits", description = "Endpoints for managing visits")
public class VisitsController {

    private final VisitCommandService visitCommandService;
    private final VisitQueryService visitQueryService;

    /**
     * Constructor for VisitsController.
     * @param visitCommandService the service for handling visit commands
     * @param visitQueryService the service for handling visit queries
     */
    public VisitsController(VisitCommandService visitCommandService, VisitQueryService visitQueryService) {
        this.visitCommandService = visitCommandService;
        this.visitQueryService = visitQueryService;
    }

    /**
     * Endpoint ro create a new visit
     * @param request the visit data to be created
     * @return a ResponseEntity containing the created visit resource or a bad request status if creation fails
     */
    @Operation(summary = "Create a new Visit",
    description = "Creates a new visit with provided data",
    requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "CreateVisitRequest object containing visit details",
        required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CreateVisitRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Visit created successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = VisitResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = InternalServerErrorResponse.class)
            ))
    })
    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(@RequestBody CreateVisitRequest request){
        var createVisitCommand = VisitAssembler.toCommandFromRequest(request);
        var visitId = this.visitCommandService.handle(createVisitCommand);

        if (Objects.isNull(visitId) || visitId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var getVisitByIdQuery = new GetVisitByIdQuery(visitId);
        var visit = this.visitQueryService.handle(getVisitByIdQuery);

        if(visit.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var visitResponse = VisitAssembler.toResponseFromEntity(visit.get());
        return new ResponseEntity<>(visitResponse, HttpStatus.CREATED);
    }


    /**
     * Endpoint to retrieve all visits
     * @return a ResponseEntity containing a list of all visit resources
     */
    @Operation(summary = "Retrieves all visits",
    description = "Fetches a list of all visits")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Visits retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = VisitResponse.class)))
            )
    })
    @GetMapping
    public ResponseEntity<List<VisitResponse>> getAllVisits(){
        var getAllVisitsQuery = new GetAllVisitsQuery();
        var visits = this.visitQueryService.handle(getAllVisitsQuery);

        var visitResponses = visits.stream()
                .map(VisitAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(visitResponses);
    }

    /**
     * Endpoint to retrieve visits by auto repair ID
     * @param autoRepairId the ID of the auto repair
     * @return a ResponseEntity containing a list of visit resources associated with the specified auto repair ID
     */
    @GetMapping("/auto-repair/{autoRepairId}")
    public ResponseEntity<List<VisitResponse>> getVisitsByAutoRepairId(@PathVariable String autoRepairId) {
        var query = new GetVisitByAutoRepairIdQuery(autoRepairId);
        var visits = visitQueryService.handle(query);

        var visitResponses = visits.stream()
                .map(VisitAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(visitResponses);
    }

    /**
     * Endpoint to retrieve a visit by its ID
     * @param visitId the ID of the visit to retrieve
     * @return a ResponseEntity containing the visit resource if found, or a not found status if the visit does not exist
     */
    @Operation(summary = "Retrieve a profile by its ID",
        description = "Fetches a visit using its unique ID",
            parameters = {
            @Parameter(in = ParameterIn.PATH, name = "visitId",
                description = "ID of the visit to retrieve",
                required = true,
                    schema = @Schema(type = "string", format = "string"))
            }
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Visit retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VisitResponse.class))
            ),
            @ApiResponse(responseCode="404", description = "Not found - The visit with the specified ID does not exist",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = NotFoundResponse.class)
            )),
    })
    @GetMapping("/{visitId}")
    public ResponseEntity<VisitResponse> getVisitById(@PathVariable String visitId){
        var getVisitByIdQuery = new GetVisitByIdQuery(visitId);
        var optionalVisit = visitQueryService.handle(getVisitByIdQuery);
        if(optionalVisit.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var visitResponse = VisitAssembler.toResponseFromEntity(optionalVisit.get());
        return ResponseEntity.ok(visitResponse);
    }

    /**
     * Endpoint to retrieve visits by vehicle ID
     * @param vehicleId the ID of the vehicle
     * @return a ResponseEntity containing a list of visit resources associated with the specified vehicle ID
     */
    @Operation(summary = "Search visits by vehicle ID",
        description = "Fetches a list of visits associated with a specific vehicle ID",
            parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "vehicleId",
                description = "ID of the vehicle to search visits for",
                required = true,
                    schema = @Schema(type = "string", format = "string"))
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ServicesController.class)) )),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
    })
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<VisitResponse>> getVisitByVehicleId(@RequestParam String vehicleId){
        var query = new GetVisitByVehicleIdQuery(vehicleId);
        var visits = this.visitQueryService.handle(query);

        var visitResponses = visits.stream()
                .map(VisitAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(visitResponses);
    }


    /**
     * Endpoint to delete a visit by its ID
     * @param visitId the ID of the visit to delete
     * @return a ResponseEntity with no content if deletion is successful, or a not found status if the visit does not exist
     */
    @Operation(summary = "Delete a visit by its ID",
        description = "Deletes a visit using its unique ID",
            parameters = {
            @Parameter(in = ParameterIn.PATH, name = "visitId",
                description = "ID of the profile to retrieve",
                required = true,
                    schema = @Schema(type = "string", format = "string"))
            }
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Visit deleted successfully"),
            @ApiResponse(responseCode="404", description = "Not found - The visit with the specified ID does not exist",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InternalServerErrorResponse.class)
            )),
    })
    @DeleteMapping("/{visitId}")
    public ResponseEntity<?> deleteVisit(@PathVariable String visitId){
        var deleteVisitCommand = new DeleteVisitCommand(visitId);
        this.visitCommandService.handle(deleteVisitCommand);
        return ResponseEntity.noContent().build();
    }

}
