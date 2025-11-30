package pe.edu.upc.prime.platform.iam.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllLocationsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetLocationByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.LocationQueryService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationRepository locationRepository;

    /**
     * Constructor for LocationQueryServiceImpl
     * @param locationRepository the location repository
     */
    public LocationQueryServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public Optional<Location> handle(GetLocationByIdQuery query) {
        return this.locationRepository.findById(Long.valueOf(query.locationId()));
    }

    @Override
    public List<Location> handle(GetAllLocationsQuery query) {
        return (List<Location>) this.locationRepository.findAll();
    }
}
