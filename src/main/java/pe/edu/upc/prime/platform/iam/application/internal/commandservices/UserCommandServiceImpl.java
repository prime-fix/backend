package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.services.UserCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.LocationRepository;
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
     * The repository to access location data.
     */
    private final LocationRepository locationRepository;

    /**
     * Constructor for UserCommandServiceImpl.
     *
     * @param userRepository the repository to access user data
     */
    public UserCommandServiceImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }


    /**
     * Handles the creation of a new user based on the provided command.
     *
     * @param command the command containing the user information
     * @return the ID of the newly created user
     */
    @Override
    public Optional<User> handle(CreateUserCommand command) {
        var name = command.name();
        var lastName = command.lastName();

        if (this.userRepository.existsByNameAndLastName(name, lastName)) {
            throw new IllegalArgumentException("[UserCommandServiceImpl] User with the same name "
                    + name + " and last name " + lastName + " already exists.");
        }

        var locationId = command.locationId();

        if (!this.locationRepository.existsById(locationId)) {
            throw new NotFoundIdException(Location.class, locationId);
        }

        var user = new User(command, this.locationRepository.findById(locationId).get());

        try {
            this.userRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while creating user: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the update of a user based on the provided command.
     *
     * @param command the command containing the updated user information
     * @return an Optional containing the updated User if successful, or empty if not found
     */
    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        var userId = command.userId();
        var name = command.name();
        var lastName = command.lastName();

        if (!this.userRepository.existsById(userId)) {
            throw new NotFoundIdException(User.class, userId);
        }

        if (this.userRepository.existsByNameAndLastNameAndIdIsNot(name, lastName, userId)) {
            throw new IllegalArgumentException("[UserCommandServiceImpl] User with the same name "
                    + name + " and last name " + lastName + " already exists.");
        }

        var locationId = command.locationId();

        if (!this.locationRepository.existsById(locationId)) {
            throw new NotFoundIdException(Location.class, locationId);
        }

        var userToUpdate = this.userRepository.findById(userId).get();

        userToUpdate.updateUser(command, this.locationRepository.findById(locationId).get());

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
        if (!this.userRepository.existsById(command.userId())) {
            throw new NotFoundIdException(User.class, command.userId());
        }

        try {
            this.userRepository.deleteById(command.userId());
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while deleting user: "
                    + e.getMessage());
        }
    }
}
