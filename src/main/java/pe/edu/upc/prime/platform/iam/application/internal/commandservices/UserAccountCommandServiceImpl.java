package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.*;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

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
        /*
        var userAccountId = command.idUserAccount();
        var username = command.username();
        var email = command.email();

        if (this.userAccountRepository.existsByIdUserAccount(userAccountId)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] User Account with the same id "
                    + userAccountId + " already exists.");
        }

        if (this.userAccountRepository.existsByUsername(userAccountId)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] User Account with the same username " +
                    username + " already exists.");
        }

        if (this.userAccountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] User Account with the same email "
                    + email + " already exists.");
        }
        */
        var userAccount = new UserAccount(command);
        try {
            this.userAccountRepository.save(userAccount);
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while creating user account: "
                    + e.getMessage());
        }
        return userAccount.getId().toString();
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

        if (!this.userAccountRepository.existsById(Long.valueOf(userAccountId))) {
            throw new NotFoundIdException(UserAccount.class, userAccountId);
        }

        if (this.userAccountRepository.existsByEmailAndIdIsNot(email, Long.valueOf(userAccountId))) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl]  Account with the same email "
                    + email + " already exists.");
        }

        if (this.userAccountRepository.existsByUsernameAndIdIsNot(username, Long.valueOf(userAccountId))) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] User Account with the same username "
                    + username + " already exists.");
        }


        var userAccountToUpdate = this.userAccountRepository.findById(Long.valueOf(userAccountId)).get();
        userAccountToUpdate.updateUserAccount(command);

        try {
            this.userAccountRepository.save(userAccountToUpdate);
            return Optional.of(userAccountToUpdate);
        } catch (Exception e) {
            throw new PersistenceException(" [UserAccountCommandServiceImpl] Error while updating user account: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a user account based on the provided command.
     *
     * @param command the command containing the ID of the user account to be deleted
     */
    @Override
    public void handle(DeleteUserAccountCommand command) {
        if(!this.userAccountRepository.existsById(Long.valueOf(command.idUserAccount()))) {
            throw new NotFoundIdException(UserAccount.class, command.idUserAccount());
        }

        try {
            this.userAccountRepository.deleteById(Long.valueOf(command.idUserAccount()));
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while deleting user account: "
                    + e.getMessage());
        }
    }
}
