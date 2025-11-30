package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateLocationCommand;

import java.util.Optional;

/**
 * Service interface for handling location-related commands.
 */
public interface LocationCommandService {
    /**
     * Handles the creation of a new Location based on the provided command
     *
     * @param command the command containing the location information
     * @return an Optional containing the created Location if successful, or empty if creation failed
     */
    Optional<Location> handle(CreateLocationCommand command);

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
