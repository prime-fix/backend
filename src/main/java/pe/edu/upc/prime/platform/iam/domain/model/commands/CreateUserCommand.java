package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to create a new user.
 *
 * @param name name of the user
 * @param lastName last name of the user
 * @param dni national identity card of the user
 * @param phoneNumber phone number of the user
 * @param idLocation identifier of the location
 */
public record CreateUserCommand( String name, String lastName, String dni, String phoneNumber, String idLocation) {

    public CreateUserCommand {
        Objects.requireNonNull(name, "[CreateUserCommand] name must not be null");
        Objects.requireNonNull(lastName, "[CreateUserCommand] last name must not be null");
        Objects.requireNonNull(dni, "[CreateUserCommand] dni must not be null");
        Objects.requireNonNull(phoneNumber, "[CreateUserCommand] phone number must not be null");
        Objects.requireNonNull(idLocation, "[CreateUserCommand] id location must not be null");

        if (dni.length() != Util.DNI_LENGTH) {
            throw new IllegalArgumentException("[CreateUserCommand] dni must be 8 characters long");
        }
    }
}
