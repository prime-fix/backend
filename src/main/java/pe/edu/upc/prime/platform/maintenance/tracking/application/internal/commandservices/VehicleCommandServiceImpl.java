package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories.VehicleRepository;
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
     * Constructor for VehicleCommandServiceImpl.
     *
     * @param vehicleRepository the vehicle repository
     */
    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Handles the creation of a new vehicle.
     *
     * @param command the command containing the vehicle information
     * @return the ID of the created vehicle
     */
    @Override
    public String handle(CreateVehicleCommand command) {
        var idVehicle = command.idVehicle();
        var vehiclePlate = command.vehicleInformation().vehiclePlate();

        if (vehicleRepository.existsById(idVehicle)) {
            throw new IllegalArgumentException("[VehicleCommandServiceImpl] Vehicle with ID "
                    + idVehicle + " already exists");
        }

        if (vehicleRepository.existsByVehicleInformation_VehiclePlate(vehiclePlate)) {
            throw new IllegalArgumentException("[VehicleCommandServiceImpl] Vehicle with the vehicle plate "
                    + vehiclePlate + " already exists");
        }

        var vehicle = new Vehicle(command);
        try {
            this.vehicleRepository.save(vehicle);
        } catch (Exception e) {
            throw new PersistenceException("[VehicleCommandServiceImpl] Error while saving vehicle: "
                    + e.getMessage());
        }
        return vehicle.getIdVehicle();
    }

    /**
     * Handles the update of an existing vehicle.
     *
     * @param command the command containing the updated vehicle information
     * @return an Optional containing the updated vehicle, or empty if not found
     */
    @Override
    public Optional<Vehicle> handle(UpdateVehicleCommand command) {
        var idVehicle = command.idVehicle();
        var vehiclePlate = command.vehicleInformation().vehiclePlate();

        if (!this.vehicleRepository.existsById(idVehicle)) {
            throw new NotFoundIdException(Vehicle.class, idVehicle);
        }
        if (this.vehicleRepository.existsByVehicleInformation_VehiclePlateAndIdVehicleIsNot(vehiclePlate, idVehicle)) {
            throw new IllegalArgumentException("[VehicleCommandServiceImpl] Another vehicle with the vehicle plate "
                    + vehiclePlate + " already exists");
        }

        var vehicleToUpdate = this.vehicleRepository.findById(idVehicle).get();
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
        if (!this.vehicleRepository.existsById(command.idVehicle())) {
            throw new NotFoundIdException(Vehicle.class, command.idVehicle());
        }

        try {
            this.vehicleRepository.deleteById(command.idVehicle());
        } catch (Exception e) {
            throw new PersistenceException("[VehicleCommandServiceImpl] Error while deleting vehicle: "
                    + e.getMessage());
        }
    }
}
