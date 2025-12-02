package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.MaintenanceStatus;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.UserId;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.CreateVehicleRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.UpdateVehicleRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.VehicleResponse;

/**
 * Assembler class for converting between Vehicle-related requests, commands, and responses.
 */
public class VehicleAssembler {

    /**
     * Converts a CreateVehicleRequest to a CreateVehicleCommand.
     *
     * @param request the CreateVehicleRequest to convert
     * @return the corresponding CreateVehicleCommand
     */
    public static CreateVehicleCommand toCommandFromRequest(CreateVehicleRequest request) {
        return new CreateVehicleCommand(
                 request.color(), request.model(), new UserId(request.userId()),
                new VehicleInformation(request.vehicleBrand(), request.vehiclePlate(), request.vehicleType())
        );
    }

    /**
     * Converts an UpdateVehicleRequest to an UpdateVehicleCommand.
     *
     * @param vehicleId the ID of the vehicle to update
     * @param request the UpdateVehicleRequest to convert
     * @return the corresponding UpdateVehicleCommand
     */
    public static UpdateVehicleCommand toCommandFromRequest(Long vehicleId, UpdateVehicleRequest request) {
        return new UpdateVehicleCommand(
                vehicleId, request.color(), request.model(), new UserId(request.userId()),
                new VehicleInformation(request.vehicleBrand(), request.vehiclePlate(), request.vehicleType()),
                MaintenanceStatus.fromValue(request.maintenanceStatus())
        );
    }

    /**
     * Converts a Vehicle entity to a VehicleResponse.
     *
     * @param entity the Vehicle entity to convert
     * @return the corresponding VehicleResponse
     */
    public static VehicleResponse toResponseFromEntity(Vehicle entity) {
        return new VehicleResponse(entity.getId(), entity.getColor(), entity.getModel(),
                entity.getUserId().value(), entity.getVehicleInformation().vehicleBrand(),
                entity.getVehicleInformation().vehiclePlate(), entity.getVehicleInformation().vehicleType(),
                entity.getMaintenanceStatus().getValue());
    }

    /**
     * Converts raw values to an UpdateVehicleCommand.
     *
     * @param vehicleId the ID of the vehicle to update
     * @param color the color of the vehicle
     * @param model the model of the vehicle
     * @param userId the ID of the user associated with the vehicle
     * @param vehicleBrand the brand of the vehicle
     * @param vehiclePlate the plate number of the vehicle
     * @param vehicleType the type of the vehicle
     * @param maintenanceStatus the maintenance status of the vehicle
     * @return the corresponding UpdateVehicleCommand
     */
    public static UpdateVehicleCommand toCommandFromValues(Long vehicleId, String color, String model, Long userId,
                                                           String vehicleBrand, String vehiclePlate,
                                                           String vehicleType, Integer maintenanceStatus) {
        return new UpdateVehicleCommand(vehicleId, color, model, new UserId(userId),
                new VehicleInformation(vehicleBrand, vehiclePlate, vehicleType),
                MaintenanceStatus.fromValue(maintenanceStatus)
        );
    }
}
