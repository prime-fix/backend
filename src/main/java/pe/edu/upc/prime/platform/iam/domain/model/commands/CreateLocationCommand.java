package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;

import java.util.Objects;

/**
 * Command to create a new location.
 *
 * @param locationInformation the information of the location to be created
 */
public record CreateLocationCommand(
        LocationInformation locationInformation) {

    /**
     * Constructor with validation.
     *
     * @param locationInformation the information of the location to be created
     */
    public CreateLocationCommand {
        Objects.requireNonNull(locationInformation, "[CreateLocationCommand] Location information must not be null");
    }
}