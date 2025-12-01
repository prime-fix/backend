package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationsByDistrictQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling location-related queries.
 */
public interface LocationQueryService {

    /**
     * Handle the query to get a location by its ID
     *
     * @param query the query containing the location ID
     * @return an optional location matching the ID
     */
    Optional<Location> handle(GetLocationByIdQuery query);

    /**
     * Handle the query to get all locations
     *
     * @param query the query to get all locations
     * @return a list of all locations
     */
    List<Location> handle(GetAllLocationsQuery query);

    /**
     * Handle the query to get locations by district
     *
     * @param query the query containing the district information
     * @return a list of locations matching the district
     */
    List<Location> handle(GetLocationsByDistrictQuery query);
}
