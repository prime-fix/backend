package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserAccountCommand;

import java.util.Optional;

/**
 * Service interface for handling user account-related commands.
 */
public interface UserAccountCommandService {

    /**
     * Handles the creation of a new user account based on the provided command.
     *
     * @param command the command containing the user account information
     * @return the ID of the newly created user account
     */
    String handle(CreateUserAccountCommand command);

    /**
     * Handles the update of a user account based on the provided command.
     *
     * @param command the command containing the updated user account information
     * @return an Optional containing the updated UserAccount if successful, or empty if not found
     */
    Optional<UserAccount> handle(UpdateUserAccountCommand command);

    /**
     * Handles the deletion of a user account based on the provided command.
     *
     * @param command the command containing the ID of the user account to be deleted
     */
    void handle(DeleteUserAccountCommand command);
}
