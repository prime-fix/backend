package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;

import java.util.Objects;

/**
 * Command to update location information.
 *
 * @param locationInformation the new location information
 */
public record UpdateLocationCommand(Long locationId, LocationInformation locationInformation) {

    /**
     * Constructor with validation.
     *
     * @param locationInformation the new location information
     */
    public UpdateLocationCommand {
        Objects.requireNonNull(locationInformation, "[UpdateLocationCommand] Location information must not be null");
    }
}
