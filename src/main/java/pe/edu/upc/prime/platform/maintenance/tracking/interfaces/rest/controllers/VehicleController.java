package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetAllVehiclesQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByMaintenanceStatusQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers.VehicleAssembler;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.CreateVehicleRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.UpdateVehicleRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.VehicleResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing vehicles.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vehicles", description = "Vehicle Management Endpoints")
public class VehicleController {
    /**
     * Service for handling vehicle queries.
     */
    private final VehicleQueryService vehicleQueryService;

    /**
     * Service for handling vehicle commands.
     */
    private final VehicleCommandService vehicleCommandService;

    /**
     * Constructor for VehicleController.
     *
     * @param vehicleQueryService initializes the vehicle query service
     * @param vehicleCommandService initializes the vehicle command service
     */
    public VehicleController(VehicleQueryService vehicleQueryService,
                             VehicleCommandService vehicleCommandService) {
        this.vehicleQueryService = vehicleQueryService;
        this.vehicleCommandService = vehicleCommandService;
    }

    /**
     * Create a new vehicle.
     *
     * @param request the vehicle creation request data
     * @return ResponseEntity with the created vehicle data
     */
    @Operation(summary = "Create a new vehicle",
            description = "Creates a new vehicle with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Vehicle data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateVehicleRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vehicle created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@RequestBody CreateVehicleRequest request) {
        var createVehicleCommand = VehicleAssembler.toCommandFromRequest(request);
        var vehicleId = this.vehicleCommandService.handle(createVehicleCommand);

        if (Objects.isNull(vehicleId) || vehicleId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var getVehicleByIdQuery = new GetVehicleByIdQuery(vehicleId);
        var vehicle = this.vehicleQueryService.handle(getVehicleByIdQuery);
        if (vehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var vehicleResponse = VehicleAssembler.toResponseFromEntity(vehicle.get());
        return new ResponseEntity<>(vehicleResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all vehicles or filter by maintenance status.
     *
     * @param maintenanceStatus optional maintenance status filter
     * @return ResponseEntity with the list of vehicles
     */
    @Operation(summary = "Retrieve all vehicles",
            description = "Retrieves all vehicles or filters them by maintenance status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(@RequestParam(required = false) Integer maintenanceStatus) {

        List<Vehicle> vehicles;
        if (Objects.isNull(maintenanceStatus)) {
            var getAllVehiclesQuery = new GetAllVehiclesQuery();
            vehicles = vehicleQueryService.handle(getAllVehiclesQuery);
        } else {
            var query = new GetVehicleByMaintenanceStatusQuery(maintenanceStatus);
            vehicles = this.vehicleQueryService.handle(query);
        }

        var vehicleResponses = vehicles.stream()
                .map(VehicleAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(vehicleResponses);
    }

    /**
     * Retrieve a vehicle by its ID.
     *
     * @param id_vehicle the unique ID of the vehicle
     * @return ResponseEntity with the vehicle data
     */
    @Operation(summary = "Retrieve a vehicle by its ID",
            description = "Retrieves a vehicle using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponse.class))),
            })
    @GetMapping("/{id_vehicle}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable String id_vehicle) {
        var getVehicleByIdQuery = new GetVehicleByIdQuery(id_vehicle);
        var optionalVehicle = vehicleQueryService.handle(getVehicleByIdQuery);
        if (optionalVehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var vehicleResponse = VehicleAssembler.toResponseFromEntity(optionalVehicle.get());
        return ResponseEntity.ok(vehicleResponse);
    }

    /**
     * Update an existing vehicle.
     *
     * @param id_vehicle the unique ID of the vehicle to update
     * @param request the vehicle update request data
     * @return ResponseEntity with the updated vehicle data
     */
    @Operation(summary = "Update an existing vehicle",
            description = "Update an existing vehicle with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Vehicle data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateVehicleRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehicleResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{id_vehicle}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable String id_vehicle,
                                                         @RequestBody UpdateVehicleRequest request) {
        var updateVehicleCommand = VehicleAssembler.toCommandFromRequest(id_vehicle, request);
        var optionalVehicle = this.vehicleCommandService.handle(updateVehicleCommand);
        if (optionalVehicle.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var vehicleResponse = VehicleAssembler.toResponseFromEntity(optionalVehicle.get());
        return ResponseEntity.ok(vehicleResponse);
    }

    /**
     * Delete a vehicle by its ID.
     *
     * @param id_vehicle the unique ID of the vehicle to delete
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete a vehicle by its ID",
            description = "Deletes a vehicle using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid vehicle ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{id_vehicle}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String id_vehicle) {
        var deleteVehicleCommand = new DeleteVehicleCommand(id_vehicle);
        this.vehicleCommandService.handle(deleteVehicleCommand);
        return ResponseEntity.noContent().build();
    }
}
