package pe.edu.upc.prime.platform.workshopCatalog.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;

import java.util.List;

@Repository
public interface AutoRepairRepository extends JpaRepository<AutoRepair, Long> {
    List<AutoRepair> findByStatus(String status);
}
