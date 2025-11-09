package pe.edu.upc.center.data_collection.data.infrastructure.persistance.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.IdVehicle;

import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit,Long> {
    Optional<Visit> findByIdVehicle(VehicleId vehicleId);
    Optional<Visit> findByIdAutoRepair(AutoRepairId autoRepairId);
    boolean existsByIdVehicle(VehicleId vehicleId);
}
