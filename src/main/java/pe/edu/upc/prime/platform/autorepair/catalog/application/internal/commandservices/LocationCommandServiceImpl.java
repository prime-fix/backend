package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteLocationCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.LocationCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.LocationRepository;

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
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving location" + e.getMessage());
        }
        return location.getId();
    }

    @Override
    public Optional<Location> handle(UpdateLocationCommand command) {
        var locationId = command.locationID();
        if (!this.locationRepository.existsById(Long.valueOf(locationId))){
            throw new IllegalArgumentException("Location does not exist");
        }
        var locationUpdate = this.locationRepository.findById(Long.valueOf(locationId)).get();
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
        if (!this.locationRepository.existsById(Long.valueOf(command.locationId()))){
            throw new  IllegalArgumentException("Location does not exist");
        }
        try {
            this.locationRepository.deleteById(Long.valueOf(command.locationId()));
        } catch (Exception e){
            throw new IllegalArgumentException("Error while deleting location" + e.getMessage());
        }
    }
}
