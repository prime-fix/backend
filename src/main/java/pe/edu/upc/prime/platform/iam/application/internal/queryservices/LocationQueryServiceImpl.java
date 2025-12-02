package pe.edu.upc.prime.platform.iam.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationsByDistrictQuery;
import pe.edu.upc.prime.platform.iam.domain.services.LocationQueryService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.LocationRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of LocationQueryService
 */
@Service
public class LocationQueryServiceImpl implements LocationQueryService {
    /**
     * The location repository
     */
    private final LocationRepository locationRepository;

    /**
     * Constructor for LocationQueryServiceImpl
     * @param locationRepository the location repository
     */
    public LocationQueryServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Handles the GetLocationByIdQuery
     *
     * @param query the query containing the location ID
     * @return an Optional containing the Location if found, otherwise empty
     */
    @Override
    public Optional<Location> handle(GetLocationByIdQuery query) {
        return this.locationRepository.findById(query.locationId());
    }

    /**
     * Handles the GetAllLocationsQuery
     *
     * @param query the query to get all locations
     * @return a list of all Locations
     */
    @Override
    public List<Location> handle(GetAllLocationsQuery query) {
        return this.locationRepository.findAll();
    }

    /**
     * Handles the GetLocationsByDistrictQuery
     *
     * @param query the query containing the district information
     * @return a list of Locations in the specified district
     */
    @Override
    public List<Location> handle(GetLocationsByDistrictQuery query) {
        return this.locationRepository.findByLocationInformation_District(query.district());
    }
}
