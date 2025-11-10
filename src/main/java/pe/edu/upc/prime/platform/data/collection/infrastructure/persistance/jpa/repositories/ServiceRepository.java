package pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

   /*List<Service> findByAutoRepairId(String autoRepairId);
}
   */
}