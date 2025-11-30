package pe.edu.upc.prime.platform.iam.domain.services;

import org.apache.commons.lang3.tuple.ImmutablePair;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.*;

import java.util.Optional;

/**
 * Service interface for handling user account-related commands.
 */
public interface UserAccountCommandService {
    /**
     * Handles the sign-in process for a user account based on the provided command.
     *
     * @param command the command containing the user account credentials
     * @return an Optional containing a pair of UserAccount and authentication token if successful, or empty if sign-in failed
     */
    Optional<ImmutablePair<UserAccount, String>> handle(SignInCommand command);

    /**
     * Handles the sign-up process for a new user account based on the provided command.
     *
     * @param command the command containing the user account information
     * @return an Optional containing the created UserAccount if successful, or empty if sign-up failed
     */
    Optional<UserAccount> handle(SignUpCommand command);

    /**
     * Handles the creation of a new user account based on the provided command.
     *
     * @param command the command containing the user account information
     * @return the ID of the newly created user account
     */
    Long handle(CreateUserAccountCommand command);

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
