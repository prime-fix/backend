package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.*;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;

import java.util.Optional;

/**
 * Implementation of the UserCommandService interface.
 */
@Service
public class UserAccountCommandServiceImpl implements UserAccountCommandService {

    /**
     * The repository to access user data.
     */
    private final UserAccountRepository userAccountRepository;

    /**
     * Constructor for UserCommandServiceImpl.
     *
     * @param userAccountRepository the repository to access user data
     */
    public UserAccountCommandServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * Handles the creation of a new user account based on the provided command.
     *
     * @param command the command containing the user account information
     * @return the ID of the newly created user account
     */
    @Override
    public String handle(CreateUserAccountCommand command) {
        var userAccountId = command.idUserAccount();
        var username = command.username();
        var email = command.email();

        if (this.userAccountRepository.existsByIdUserAccount(userAccountId)) {
            throw new IllegalArgumentException("User Account with the same id " + userAccountId + " already exists.");
        }

        if (this.userAccountRepository.existsByUsernameAndIdUserAccountIsNot(username, userAccountId)) {
            throw new IllegalArgumentException("User Account with the same username " + username + " already exists.");
        }

        if (this.userAccountRepository.existsByEmailAndIdUserAccountIsNot(email, userAccountId)) {
            throw new IllegalArgumentException("User Account with the same email " + email + " already exists.");
        }

        var userAccount = new UserAccount(command);
        try {
            this.userAccountRepository.save(userAccount);
            return userAccount.getIdUserAccount();
        } catch (Exception e) {
            throw new PersistenceException("Error creating user account: " + e.getMessage());
        }
    }

    /**
     * Handles the update of an existing user account based on the provided command.
     *
     * @param command the command containing the updated user account information
     * @return an Optional containing the updated UserAccount if successful
     */
    @Override
    public Optional<UserAccount> handle(UpdateUserAccountCommand command) {
        var userAccountId = command.idUserAccount();
        var username = command.username();
        var email = command.email();

        if (this.userAccountRepository.existsByEmailAndIdUserAccountIsNot(email, userAccountId)) {
            throw new IllegalArgumentException("User Account with the same email " + email + " already exists.");
        }

        if (this.userAccountRepository.existsByUsernameAndIdUserAccountIsNot(username, userAccountId)) {
            throw new IllegalArgumentException("User Account with the same username " + username + " already exists.");
        }

        if (!this.userAccountRepository.existsByIdUserAccount(userAccountId)) {
            throw new NotFoundArgumentException(
                    String.format("UserAccount with the same id %s does not exist.", userAccountId)
            );
        }

        var userAccountToUpdate = this.userAccountRepository.findById(userAccountId).get();
        userAccountToUpdate.updateUserAccount(command);

        try {
            this.userAccountRepository.save(userAccountToUpdate);
            return Optional.of(userAccountToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("Error updating user account: " + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a user account based on the provided command.
     *
     * @param command the command containing the ID of the user account to be deleted
     */
    @Override
    public void handle(DeleteUserAccountCommand command) {
        if(!this.userAccountRepository.existsByIdUserAccount(command.idUserAccount())) {
            throw new NotFoundArgumentException(
                    String.format("UserAccount with id %s does not exist.", command.idUserAccount()));
        }

        this.userAccountRepository.findById(command.idUserAccount()).ifPresent(optionalUserAccount -> {
            this.userAccountRepository.deleteById(optionalUserAccount.getIdUserAccount());
        });
    }
}
