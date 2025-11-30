package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
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
                 request.color(), request.model(), request.idUser(),
                new VehicleInformation(request.vehicleBrand(), request.vehiclePlate(), request.vehicleType()),
                request.maintenanceStatus()
        );
    }

    /**
     * Converts an UpdateVehicleRequest to an UpdateVehicleCommand.
     *
     * @param idVehicle the ID of the vehicle to update
     * @param request the UpdateVehicleRequest to convert
     * @return the corresponding UpdateVehicleCommand
     */
    public static UpdateVehicleCommand toCommandFromRequest(Long idVehicle, UpdateVehicleRequest request) {
        return new UpdateVehicleCommand(
                idVehicle, request.color(), request.model(), request.idUser(),
                new VehicleInformation(request.vehicleBrand(), request.vehiclePlate(), request.vehicleType()),
                request.maintenanceStatus()
        );
    }

    /**
     * Converts a Vehicle entity to a VehicleResponse.
     *
     * @param entity the Vehicle entity to convert
     * @return the corresponding VehicleResponse
     */
    public static VehicleResponse toResponseFromEntity(Vehicle entity) {
        return new VehicleResponse(entity.getId().toString(), entity.getColor(), entity.getModel(),
                entity.getIdUser(), entity.getVehicleInformation().vehicleBrand(),
                entity.getVehicleInformation().vehiclePlate(), entity.getVehicleInformation().vehicleType(),
                entity.getMaintenanceStatus());
    }
}
