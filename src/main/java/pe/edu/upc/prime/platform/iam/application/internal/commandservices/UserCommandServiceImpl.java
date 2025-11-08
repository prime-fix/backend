package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.services.UserCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the UserCommandService interface.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    /**
     * The repository to access user data.
     */
    private final UserRepository userRepository;

    /**
     * Constructor for UserCommandServiceImpl.
     *
     * @param userRepository the repository to access user data
     */
    public UserCommandServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }


    /**
     * Handles the creation of a new user based on the provided command.
     *
     * @param command the command containing the user information
     * @return the ID of the newly created user
     */
    @Override
    public String handle(CreateUserCommand command) {
        var userId = command.idUser();
        var name = command.name();
        var lastName = command.lastName();

        if (this.userRepository.existsByIdUser(userId)) {
            throw new IllegalArgumentException("[UserCommandServiceImpl] User with the same id "
                    + userId + " already exists.");
        }
        if (this.userRepository.existsByNameAndLastName(name, lastName)) {
            throw new IllegalArgumentException("[UserCommandServiceImpl] User with the same name "
                    + name + " and last name " + lastName + " already exists.");
        }
        var user = new User(command);
        try {
            this.userRepository.save(user);

        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while creating user: "
                    + e.getMessage());
        }
        return user.getIdUser();
    }

    /**
     * Handles the update of a user based on the provided command.
     *
     * @param command the command containing the updated user information
     * @return an Optional containing the updated User if successful, or empty if not found
     */
    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        var userId = command.idUser();
        var name = command.name();
        var lastName = command.lastName();

        if (!this.userRepository.existsByIdUser(userId)) {
            throw new NotFoundIdException(User.class, userId);
        }

        if (this.userRepository.existsByNameAndLastNameAndIdUserIsNot(userId, lastName, userId)) {
            throw new IllegalArgumentException("[UserCommandServiceImpl] User with the same name "
                    + name + " and last name " + lastName + " already exists.");
        }

        var userToUpdate = this.userRepository.findById(userId).get();
        userToUpdate.updateUser(command);

        try {
            var updatedUser = this.userRepository.save(userToUpdate);
            return Optional.of(updatedUser);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while updating user: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a user based on the provided command.
     *
     * @param command the command containing the ID of the user to be deleted
     */
    @Override
    public void handle(DeleteUserCommand command) {
        if (!this.userRepository.existsByIdUser(command.idUser())) {
            throw new NotFoundIdException(User.class, command.idUser());
        }

        try {
            this.userRepository.deleteById(command.idUser());
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while deleting user: "
                    + e.getMessage());
        }
    }
}
