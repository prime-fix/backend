package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to update an existing user.
 *
 * @param userId identifier of the user to be updated
 * @param name name of the user to be updated
 * @param lastName last name of the user to be updated
 * @param dni national identity card of the user to be updated
 * @param phoneNumber phone number of the user to be updated
 * @param locationId identifier of the location associated with the user to be updated
 */
public record UpdateUserCommand(Long userId, String name, String lastName, String dni, String phoneNumber, Long locationId) {

    /**
     * Constructor with validations.
     *
     * @param name the name of the user to be updated
     * @param lastName the last name of the user to be updated
     * @param dni the dni of the user to be updated
     * @param phoneNumber the phone number of the user to be updated
     * @param locationId the location id associated with the user to be updated
     */
    public UpdateUserCommand {
        Objects.requireNonNull(userId, "[UpdateUserCommand] user id must not be null");
        Objects.requireNonNull(name, "[UpdateUserCommand] name must not be null");
        Objects.requireNonNull(lastName, "[UpdateUserCommand] last name must not be null");
        Objects.requireNonNull(dni, "[UpdateUserCommand] dni must not be null");
        Objects.requireNonNull(phoneNumber, "[UpdateUserCommand] phoneNumber must not be null");
        Objects.requireNonNull(locationId, "[UpdateUserCommand] id location must not be null");

        if (dni.length() != Util.DNI_LENGTH) {
            throw new IllegalArgumentException("[UpdateUserCommand] dni must be 8 characters long");
        }
    }
}
