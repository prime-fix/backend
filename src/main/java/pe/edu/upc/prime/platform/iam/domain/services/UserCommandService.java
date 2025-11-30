package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserCommand;

import java.util.Optional;

/**
 * Service interface for handling user-related commands.
 */
public interface UserCommandService {
    /**
     * Handles the creation of a new user based on the provided command.
     *
     * @param command the command containing the user information
     * @return the ID of the newly created user
     */
    Optional<User> handle(CreateUserCommand command);

    /**
     * Handles the update of a user based on the provided command.
     *
     * @param command the command containing the updated user information
     * @return an Optional containing the updated User if successful, or empty if not found
     */
    Optional<User> handle(UpdateUserCommand command);

    /**
     * Handles the deletion of a user based on the provided command.
     *
     * @param command the command containing the ID of the user to be deleted
     */
    void handle(DeleteUserCommand command);
}
