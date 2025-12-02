package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.ExpectedVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllExpectedVisitsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetExpectedVisitByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling expected visit queries.
 */
public interface ExpectedVisitQueryService {
    /**
     * Handle the query to get all expected visits.
     *
     * @param query the query to get all expected visits
     * @return a list of all expected visits
     */
    List<ExpectedVisit> handle(GetAllExpectedVisitsQuery query);

    /**
     * Handle the query to get an expected visit by its ID.
     *
     * @param query the query to get an expected visit by ID
     * @return an Optional containing the expected visit if found, or empty if not found
     */
    Optional<ExpectedVisit> handle(GetExpectedVisitByIdQuery query);
}
