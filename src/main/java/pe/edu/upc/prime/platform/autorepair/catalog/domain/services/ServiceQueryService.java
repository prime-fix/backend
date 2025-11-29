package pe.edu.upc.prime.platform.autorepair.catalog.domain.services;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAllServicesQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetServiceByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling service-related queries
 */
public interface ServiceQueryService {
    /*List<Service> handle(GetServiceByAutoRepairIdQuery query);*/

    /**
     * Handles the query to get a service by its ID
     * @param query the query containing the service ID
     * @return an optional service matching the ID
     */
    Optional<Service> handle(GetServiceByIdQuery query);

    /**
     * Handle the query to get all services
     * @param query the query to get all services
     * @return a list of all services
     */
    List<Service> handle(GetAllServicesQuery query);
}
