package pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.ExpectedVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VisitId;

/**
 * Repository interface for managing ExpectedVisit entities.
 */
public interface ExpectedVisitRepository extends JpaRepository<ExpectedVisit, Long> {

    /**
     * Checks if an ExpectedVisit exists by the given VisitId.
     *
     * @param visitId the VisitId to check
     * @return true if an ExpectedVisit with the given VisitId exists, false otherwise
     */
    boolean existsByVisitId(VisitId visitId);

    /**
     * Checks if an ExpectedVisit exists by the given VisitId, excluding a specific ExpectedVisit ID.
     *
     * @param visitId the VisitId to check
     * @param id the ID of the ExpectedVisit to exclude from the check
     * @return true if an ExpectedVisit with the given VisitId exists excluding the specified ID, false otherwise
     */
    boolean existsByVisitIdAndIdIsNot(VisitId visitId, Long id);
}
