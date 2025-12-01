package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.services.LocationCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.LocationRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of LocationCommandService
 */
@Service
public class LocationCommandServiceImpl implements LocationCommandService {

    private final LocationRepository locationRepository;

    /**
     * Constructor for LocationCommandServiceImpl
     * @param locationRepository the location repository
     */
    public LocationCommandServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public Long handle(CreateLocationCommand command) {
        var location = new Location(command);

        try{
            this.locationRepository.save(location);
            return location.getId();
        } catch (Exception e){
            throw new IllegalArgumentException("[LocationCommandServiceImpl] Error while saving location" + e.getMessage());
        }
    }

    @Override
    public Optional<Location> handle(UpdateLocationCommand command) {
        var locationId = command.locationId();

        if (!this.locationRepository.existsById(locationId)){
            throw new IllegalArgumentException("[LocationCommandServiceImpl] Location does not exist");
        }

        var locationUpdate = this.locationRepository.findById(locationId).get();
        locationUpdate.updateLocation(command);

        try {
            this.locationRepository.save(locationUpdate);
            return Optional.of(locationUpdate);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving location" + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteLocationCommand command) {
        if (!this.locationRepository.existsById(command.locationId())){
            throw new NotFoundIdException(Location.class, command.locationId());
        }

        try {
            this.locationRepository.deleteById(command.locationId());
        } catch (Exception e){
            throw new IllegalArgumentException("[LocationCommandServiceImpl] Error while deleting location" + e.getMessage());
        }
    }
}
