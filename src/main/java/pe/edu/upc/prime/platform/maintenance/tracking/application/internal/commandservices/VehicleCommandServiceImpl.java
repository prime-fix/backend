package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.application.internal.outboundservices.acl.ExternalIamServiceFromMaintenanceTracking;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories.VehicleRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of VehicleCommandService.
 */
@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {
    /**
     * The vehicle repository.
     */
    private final VehicleRepository vehicleRepository;

    /**
     * The external IAM service.
     */
    private final ExternalIamServiceFromMaintenanceTracking externalIamServiceFromMaintenanceTracking;

    /**
     * Constructor for VehicleCommandServiceImpl.
     *
     * @param vehicleRepository the vehicle repository
     */
    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository,
                                     ExternalIamServiceFromMaintenanceTracking externalIamServiceFromMaintenanceTracking) {
        this.vehicleRepository = vehicleRepository;
        this.externalIamServiceFromMaintenanceTracking = externalIamServiceFromMaintenanceTracking;
    }

    /**
     * Handles the creation of a new vehicle.
     *
     * @param command the command containing the vehicle information
     * @return the ID of the created vehicle
     */
    @Override
    public Long handle(CreateVehicleCommand command) {
        var vehiclePlate = command.vehicleInformation().vehiclePlate();

        // Validate if vehicle plate already exists
        if (vehicleRepository.existsByVehicleInformation_VehiclePlate(vehiclePlate)) {
            throw new IllegalArgumentException("[VehicleCommandServiceImpl] Vehicle with the vehicle plate "
                    + vehiclePlate + " already exists");
        }

        // Validate if user ID exists in external IAM service
        if (!this.externalIamServiceFromMaintenanceTracking.existsUserById(command.userId().userId())) {
            throw new NotFoundArgumentException(
                    String.format("[VehicleCommandServiceImpl User ID: %s not found in the external IAM service",
                            command.userId().userId()));
        }

        // Create and save the new vehicle
        var vehicle = new Vehicle(command);
        try {
            this.vehicleRepository.save(vehicle);
        } catch (Exception e) {
            throw new PersistenceException("[VehicleCommandServiceImpl] Error while saving vehicle: "
                    + e.getMessage());
        }
        return vehicle.getId();
    }

    /**
     * Handles the update of an existing vehicle.
     *
     * @param command the command containing the updated vehicle information
     * @return an Optional containing the updated vehicle, or empty if not found
     */
    @Override
    public Optional<Vehicle> handle(UpdateVehicleCommand command) {
        var vehicleId = command.vehicleId();
        var vehiclePlate = command.vehicleInformation().vehiclePlate();

        // Validate if vehicle ID exists
        if (!this.vehicleRepository.existsById(vehicleId)) {
            throw new NotFoundIdException(Vehicle.class, vehicleId);
        }

        // Validate if another vehicle with the same vehicle plate exists
        if (this.vehicleRepository.existsByVehicleInformation_VehiclePlateAndIdIsNot(vehiclePlate, vehicleId)) {
            throw new IllegalArgumentException("[VehicleCommandServiceImpl] Another vehicle with the vehicle plate "
                    + vehiclePlate + " already exists");
        }

        // Validate if user ID exists in external IAM service
        if (!this.externalIamServiceFromMaintenanceTracking.existsUserById(command.userId().userId())) {
            throw new NotFoundArgumentException(
                    String.format("[VehicleCommandServiceImpl User ID: %s not found in the external IAM service",
                            command.userId().userId()));
        }

        // Update and save the vehicle
        var vehicleToUpdate = this.vehicleRepository.findById(vehicleId).get();
        vehicleToUpdate.updateVehicle(command);

        try {
            this.vehicleRepository.save(vehicleToUpdate);
            return Optional.of(vehicleToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[VehicleCommandServiceImpl] Error while updating vehicle: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a vehicle.
     *
     * @param command the command containing the ID of the vehicle to be deleted
     */
    @Override
    public void handle(DeleteVehicleCommand command) {
        if (!this.vehicleRepository.existsById(command.vehicleId())) {
            throw new NotFoundIdException(Vehicle.class, command.vehicleId());
        }

        try {
            this.vehicleRepository.deleteById(command.vehicleId());
        } catch (Exception e) {
            throw new PersistenceException("[VehicleCommandServiceImpl] Error while deleting vehicle: "
                    + e.getMessage());
        }
    }
}
