package pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.MaintenanceStatus;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;

import java.util.List;

/**
 * Repository interface for managing Vehicle entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations and custom query methods
 *     for the Vehicle entity.</p>
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    /**
     * Check if a vehicle exists by its vehicle plate.
     *
     * @param vehiclePlate the plate number of the vehicle
     * @return true if a vehicle with the given plate exists, false otherwise
     */
    boolean existsByVehicleInformation_VehiclePlate(String vehiclePlate);

    /**
     * Check if a vehicle exists by its vehicle plate excluding a specific vehicle ID.
     *
     * @param vehiclePlate the plate number to check
     * @param id the ID of the vehicle to exclude
     * @return true if another vehicle exists with the given plate excluding the specified ID
     */
    boolean existsByVehicleInformation_VehiclePlateAndIdIsNot(String vehiclePlate, Long id);

    /**
     * Find vehicles by maintenance status.
     *
     * @param maintenanceStatus the maintenance status to filter by
     * @return a list of vehicles with the given maintenance status
     */
    List<Vehicle> findByMaintenanceStatus(MaintenanceStatus maintenanceStatus);
}
