package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to create a new user.
 *
 * @param idUser identifier of the user
 * @param name name of the user
 * @param lastName last name of the user
 * @param dni national identity card of the user
 * @param phoneNumber phone number of the user
 */
public record CreateUserCommand(String idUser, String name, String lastName, String dni, String phoneNumber) {

    public CreateUserCommand {
        Objects.requireNonNull(idUser, "[CreateUserCommand] id must not be null");
        Objects.requireNonNull(name, "[CreateUserCommand] name must not be null");
        Objects.requireNonNull(lastName, "[CreateUserCommand] last name must not be null");
        Objects.requireNonNull(dni, "[CreateUserCommand] dni must not be null");
        Objects.requireNonNull(phoneNumber, "[CreateUserCommand] phoneNumber must not be null");

        if (dni.length() != Util.DNI_LENGTH) {
            throw new IllegalArgumentException("[CreateUserCommand] dni must be 8 characters long");
        }
    }
}
