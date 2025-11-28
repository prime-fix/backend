package pe.edu.upc.prime.platform.workshopCatalog.domain.services;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.GetLocationByIdQuery;

import java.util.List;
import java.util.Optional;

public interface LocationQueryService {

    /**
     * Handle the query to get a location by its ID
     * @param query the query containing the location ID
     * @return an optional location matching the ID
     */
    Optional<Location> handle(GetLocationByIdQuery query);

    /**
     * Handle the query to get all locations
     * @param query the query to get all locations
     * @return a list of all locations
     */
    List<Location> handle(GetAllLocationsQuery query);
}
