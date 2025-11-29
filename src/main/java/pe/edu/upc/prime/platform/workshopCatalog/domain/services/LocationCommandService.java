package pe.edu.upc.prime.platform.workshopCatalog.domain.services;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.DeleteLocationCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.UpdateLocationCommand;

import java.util.Optional;

public interface LocationCommandService {


    /**
     * Handles the creation of a new Location based on the provided command
     *
     * @param command the command containing the location information
     * @return the ID of the newly created location
     */
    Long handle(CreateLocationCommand command);

    /**
     * Handle the update of a location based on the provided command
     * @param command the command containing the updated location information
     * @return an Optional containing the update Location if successful, or empty if not found
     */
    Optional<Location> handle(UpdateLocationCommand command);

    /**
     * Handle the delection of a Location based on the provided command
     * @param command the command containing the ID of the location to be related
     */
    void handle(DeleteLocationCommand command);
}
