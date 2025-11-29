package pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;

import java.util.List;

/**
 * Repository interface for managing Visit entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations and custom query methods
 * for the Visit entity</p>
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit,Long> {
    /**
     * Custom query method to check the existence of a visit by vehicleId
     * @param vehicleId the vehicleId to search for vehicleId
     * @return an Optional containing the found Visit if found, or empty if not found
     */
    List<Visit> findByVehicleId_VehicleId(Long vehicleId);

    /**
     * Custom query method to find visits by autoRepairId
     * @param autoRepairId the autoRepairId to search for ID
     * @return a list of visits matching the auto repair ID
     */
    List<Visit> findByAutoRepairId_AutoRepairId(Long autoRepairId);

    /**
     * Custom query method to check the existence of a visit by autoRepairId
     * @param autoRepairId the auto repair ID to check for existence
     * @return true if a visit with the given autoRepairId exists
     */
    boolean existsByAutoRepairId_AutoRepairId(Long autoRepairId);

    /**
     * Custom query method to check the existence of a visit by VehicleId
     * @param vehicleId the vehicle ID to check for existence
     * @return true if a visit with the given vehicleId exists
     */
    boolean existsByVehicleId_VehicleId( Long vehicleId);
}
