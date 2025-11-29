package pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}
