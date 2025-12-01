package pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

   /*List<Service> findByAutoRepairId(String autoRepairId); */
}