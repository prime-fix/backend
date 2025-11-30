package pe.edu.upc.prime.platform.iam.domain.model.commands;

import java.util.Objects;

/**
 * Command to delete a location by its ID.
 *
 * @param locationId the ID of the location to be deleted
 */
public record DeleteLocationCommand(Long locationId) {

    /**
     * Constructor with validation.
     *
     * @param locationId the ID of the location to be deleted
     */
    public DeleteLocationCommand {
        Objects.requireNonNull(locationId, "[DeleteLocationCommand] The location id must not be null");
    }
}
