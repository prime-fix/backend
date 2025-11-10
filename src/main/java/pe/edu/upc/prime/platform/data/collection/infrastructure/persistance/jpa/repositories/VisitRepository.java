package pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit,Long> {
    List<Visit> findByVehicleId(String vehicleId);
    List<Visit> findByAutoRepairId(String autoRepairId);

}
