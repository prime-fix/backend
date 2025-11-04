package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to update an existing user.
 *
 * @param idUser identifier of the user to be updated
 * @param name name of the user to be updated
 * @param lastName last name of the user to be updated
 * @param dni national identity card of the user to be updated
 * @param phoneNumber phone number of the user to be updated
 */
public record UpdateUserCommand(String idUser, String name, String lastName, String dni, String phoneNumber) {

    public UpdateUserCommand {
        Objects.requireNonNull(idUser, "[UpdateUserCommand] id must not be null");
        Objects.requireNonNull(name, "[UpdateUserCommand] name must not be null");
        Objects.requireNonNull(lastName, "[UpdateUserCommand] last name must not be null");
        Objects.requireNonNull(dni, "[UpdateUserCommand] dni must not be null");
        Objects.requireNonNull(phoneNumber, "[UpdateUserCommand] phoneNumber must not be null");

        if (dni.length() != Util.DNI_LENGTH) {
            throw new IllegalArgumentException("[CreateUserCommand] dni must be 8 characters long");
        }
    }
}
