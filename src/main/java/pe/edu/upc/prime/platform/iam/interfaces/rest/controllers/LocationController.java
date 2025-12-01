package pe.edu.upc.prime.platform.iam.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationsByDistrictQuery;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.LocationCommandService;
import pe.edu.upc.prime.platform.iam.domain.services.LocationQueryService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.LocationAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateLocationRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.LocationResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateLocationRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing locations.
 */
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping(value = "/api/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Locations", description = "Endpoints for managing locations")
public class LocationController {
    /**
     * Service for handling location commands.
     */
    private final LocationCommandService locationCommandService;
    /**
     * Service for handling location queries.
     */
    private final LocationQueryService locationQueryService;

    /**
     * Constructor for LocationController.
     *
     * @param locationCommandService the location command service
     * @param locationQueryService  the location query service
     */
    public LocationController(LocationCommandService locationCommandService, LocationQueryService locationQueryService) {
        this.locationCommandService = locationCommandService;
        this.locationQueryService = locationQueryService;
    }

    /**
     * Create a new location.
     *
     * @param request the request body containing location details
     * @return a ResponseEntity with the created LocationResponse
     */
    @Operation(summary = "Register a new Location",
            description = "Creates a new location with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CreateLocationRequest object containing location details",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateLocationRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LocationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalError.class))),
            @ApiResponse(responseCode = "503", description = "Location unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = HttpServerErrorException.InternalServerError.class)
                    ))
    })
    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody CreateLocationRequest request) {
        var createLocationCommand = LocationAssembler.toCommandFromRequest(request);
        var locationId = this.locationCommandService.handle(createLocationCommand);

        if (Objects.isNull(locationId) || locationId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getLocationByIdQuery = new GetLocationByIdQuery(locationId);
        var location = this.locationQueryService.handle(getLocationByIdQuery);

        if (location.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var locationResponse = LocationAssembler.toResponseFromEntity(location.get());
        return new ResponseEntity<>(locationResponse, HttpStatus.CREATED);

    }

    /**
     * Get all locations.
     *
     * @return a list of LocationResponse objects
     */
    @Operation(summary = "Retrieves all locations",
            description = "Fetches a list of all locations or filters them by district if provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = LocationResponse.class)))
            )
    })
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations(@RequestParam(required = false) String district) {
        List<Location> locations;

        if (Objects.isNull(district)) {
            var getAllLocationsQuery = new GetAllLocationsQuery();
            locations = this.locationQueryService.handle(getAllLocationsQuery);
        } else {
            var getLocationsByDistrictQuery = new GetLocationsByDistrictQuery(district);
            locations = this.locationQueryService.handle(getLocationsByDistrictQuery);
        }

        var locationResponses = locations.stream()
                .map(LocationAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(locationResponses);
    }

    /**
     * Get a location by its ID.
     *
     * @param locationId the ID of the location to retrieve
     * @return a ResponseEntity with the LocationResponse
     */
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long locationId) {
        var getLocationByIdQuery = new GetLocationByIdQuery(locationId);
        var optionalLocation = locationQueryService.handle(getLocationByIdQuery);
        if(optionalLocation.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var locationResponse = LocationAssembler.toResponseFromEntity(optionalLocation.get());
        return ResponseEntity.ok(locationResponse);
    }


    /**
     * Update a location by its ID.
     *
     * @param locationId the ID of the location to update
     * @param request the request body containing updated location details
     * @return a ResponseEntity with the updated LocationResponse
     */
    @PutMapping("/{locationId}")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable Long locationId, @RequestBody UpdateLocationRequest request) {
        var updateLocationCommand = LocationAssembler.toCommandFromRequest(locationId, request);
        var optionalLocation = this.locationCommandService.handle(updateLocationCommand);

        if(optionalLocation.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var locationResponse = LocationAssembler.toResponseFromEntity(optionalLocation.get());
        return ResponseEntity.ok(locationResponse);
    }

    /**
     * Delete a location by its ID.
     *
     * @param locationId the ID of the location to delete
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Delete a location by its ID",
    description = "Deletes a location using unique ID",
    parameters = {
            @Parameter(in = ParameterIn.PATH, name = "locationId",
            description = "Id of the location to retrieve",
            required = true,
            schema = @Schema(type = "string", format = "string"))
    })
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
            @ApiResponse(responseCode="404", description = "Not found - The location with the specified ID does not exist",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)
                    )),
    })
    @DeleteMapping("/{locationId}")
    public ResponseEntity<LocationResponse> deleteLocation(@PathVariable Long locationId) {
        var deleteLocationCommand = new DeleteLocationCommand(locationId);
        this.locationCommandService.handle(deleteLocationCommand);
        return ResponseEntity.noContent().build();
    }
}
