package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to create a new user
 * @param name the name of the user
 * @param lastName the last name of the user
 * @param dni the dni of the user
 * @param phoneNumber the phone number of the user
 * @param locationId the id of the location of the user
 */
public record CreateUserCommand(String name, String lastName, String dni, String phoneNumber, Long locationId) {

    /**
     * Constructor with validations
     *
     * @param name the name of the user
     * @param lastName the last name of the user
     * @param dni the dni of the user
     * @param phoneNumber the phone number of the user
     * @param locationId the id of the location of the user
     */
    public CreateUserCommand {
        Objects.requireNonNull(name, "[CreateUserCommand] name must not be null");
        Objects.requireNonNull(lastName, "[CreateUserCommand] last name must not be null");
        Objects.requireNonNull(dni, "[CreateUserCommand] dni must not be null");
        Objects.requireNonNull(phoneNumber, "[CreateUserCommand] phone number must not be null");
        Objects.requireNonNull(locationId, "[CreateUserCommand] id location must not be null");

        if (dni.length() != Util.DNI_LENGTH) {
            throw new IllegalArgumentException("[CreateUserCommand] dni must be 8 characters long");
        }
    }
}
