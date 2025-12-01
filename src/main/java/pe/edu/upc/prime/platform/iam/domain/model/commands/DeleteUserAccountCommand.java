package pe.edu.upc.prime.platform.iam.domain.model.commands;

import java.util.Objects;

/**
 * Command to delete a user account by its ID.
 *
 * @param userAccountId the ID of the user account to be deleted
 */
public record DeleteUserAccountCommand(Long userAccountId) {

    public DeleteUserAccountCommand {
        Objects.requireNonNull(userAccountId, "[DeleteUserAccountCommand] User Account ID must not be null");
    }
}
