package pe.edu.upc.prime.platform.iam.domain.model.commands;

import java.util.Objects;

/**
 * Command to delete a user by its ID.
 *
 * @param userId the ID of the user to be deleted
 */
public record DeleteUserCommand(Long userId) {

    public DeleteUserCommand {
        Objects.requireNonNull(userId, "[DeleteUserCommand] User ID must not be null");
    }
}
