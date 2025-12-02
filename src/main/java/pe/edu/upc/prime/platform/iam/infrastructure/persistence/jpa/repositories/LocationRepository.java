package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;

import java.util.List;

/**
 * Repository interface for Location entity.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find locations by district in location information.
     *
     * @param locationInformationDistrict the district to search for
     * @return a list of locations matching the district
     */
    List<Location> findByLocationInformation_District(String locationInformationDistrict);
}
