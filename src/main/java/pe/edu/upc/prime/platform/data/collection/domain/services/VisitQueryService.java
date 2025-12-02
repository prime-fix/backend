package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling visit-related queries.
 */
public interface VisitQueryService {

    /**
     * Handle the query to get all visits.
     * @param query the query to get all visits
     * @return a list of all visits
     */
    List<Visit> handle(GetAllVisitsQuery query);

    /**
     * Handle the query to get a visit by its ID.
     * @param query the query containing the visit ID
     * @return an optional visit matching the ID
     */
    Optional<Visit> handle(GetVisitByIdQuery query);

    /**
     * Handle the query to get visits by vehicle ID.
     * @param query the query containing the vehicle ID
     * @return a list of visits matching the vehicle ID
     */
    List<Visit> handle(GetVisitByVehicleIdQuery query);

    /**
     * Handle the query to get visits by auto repair ID.
     * @param query the query containing the auto repair ID
     * @return a list of visits matching the auto repair ID
     */
    List<Visit> handle(GetVisitByAutoRepairIdQuery query);

    /**
     * Handle the query to check if a visit exists by its ID.
     *
     * @param query the query containing the visit ID
     * @return true if the visit exists, false otherwise
     */
    boolean handle(ExistsVisitByIdQuery query);
}
