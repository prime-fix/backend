package pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;

import java.util.List;

/**
 * Repository interface for managing Vehicle entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations and custom query methods
 *     for the Vehicle entity.</p>
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    /**
     * Custom query method to check the existence of a vehicle by vehicle information.
     * @param vehicleInformationVehiclePlate the vehicle information to check for existence
     * @return true if a vehicle with the given information exists, false otherwise
     */
    boolean existsByVehicleInformation_VehiclePlate(String vehicleInformationVehiclePlate);

    /**
     * Custom query method to check the existence of a vehicle by vehicle information,
     *
     * @param vehicleInformationVehiclePlate the vehicle plate to check for existence
     * @param idVehicle the vehicle ID to exclude from the check
     * @return true if a vehicle with the given information exists excluding the specified vehicle ID, false otherwise
     */
    boolean existsByVehicleInformation_VehiclePlateAndIdVehicleIsNot(String vehicleInformationVehiclePlate, String idVehicle);

    /**
     * Custom query method to find vehicles by maintenance status.
     *
     * @param maintenanceStatus the maintenance status to search for
     * @return a list of vehicles with the given maintenance status
     */
    List<Vehicle> findByMaintenanceStatus(int maintenanceStatus);
}
