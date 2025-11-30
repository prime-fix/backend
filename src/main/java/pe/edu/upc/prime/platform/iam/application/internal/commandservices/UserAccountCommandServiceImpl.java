package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.prime.platform.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.prime.platform.iam.application.internal.outboundservices.tokens.TokenService;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.*;
import pe.edu.upc.prime.platform.iam.domain.services.*;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.*;
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
     * The location command service for managing user locations.
     */
    private final LocationCommandService locationCommandService;

    /**
     * The membership command service for managing user memberships.
     */
    private final MembershipCommandService membershipCommandService;

    /**
     * The membership repository for accessing membership data.
     */
    private final MembershipRepository membershipRepository;

    /**
     * The user command service for additional user operations.
     */
    private final UserCommandService userCommandService;

    /**
     * The user query service for retrieving user information.
     */
    private final UserRepository userRepository;

    /**
     * The hashing service for password management.
     */
    private final HashingService hashingService;

    /**
     * The token service for authentication tokens.
     */
    private final TokenService tokenService;

    /**
     * The repository to access role data.
     */
    private final RoleRepository roleRepository;

    /**
     * Constructor for UserAccountCommandServiceImpl.
     *
     * @param userAccountRepository the user account repository
     * @param locationCommandService the location command service
     * @param membershipCommandService the membership command service
     * @param userCommandService the user command service
     * @param hashingService the hashing service
     * @param tokenService the token service
     * @param roleRepository the role repository
     */
    public UserAccountCommandServiceImpl(UserAccountRepository userAccountRepository,
                                         LocationCommandService locationCommandService,
                                         MembershipCommandService membershipCommandService,
                                         MembershipRepository membershipRepository,
                                         UserCommandService userCommandService,
                                         UserRepository userRepository,
                                         HashingService hashingService,
                                         TokenService tokenService,
                                         RoleRepository roleRepository) {
        this.userAccountRepository = userAccountRepository;
        this.locationCommandService = locationCommandService;
        this.membershipCommandService = membershipCommandService;
        this.membershipRepository = membershipRepository;
        this.userCommandService = userCommandService;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Handles the sign-in command by validating user credentials and generating an authentication token.
     *
     * @param command the command containing the user account credentials
     * @return an optional pair of the authenticated user account and the generated token
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<ImmutablePair<UserAccount, String>> handle(SignInCommand command) {
        var userAccount = userAccountRepository.findByUsername(command.username());
        if (userAccount.isEmpty()) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] User Account not found");
        }

        if (!hashingService.matches(command.password(), userAccount.get().getPassword())) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Invalid password");
        }

        var token = tokenService.generateToken(userAccount.get().getUsername());
        return Optional.of(ImmutablePair.of(userAccount.get(), token));
    }

    /**
     * Handles the sign-up command by creating a new user account along with associated entities.
     *
     * @param command the command containing the user account information
     * @return an optional of the newly created user account
     */
    @Transactional
    @Override
    public Optional<UserAccount> handle(SignUpCommand command) {
        var roleId = command.roleId();

        if (userAccountRepository.existsByUsername(command.username())) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Username already exists");
        }

        if (!roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found");
        }

        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found"));

        var location = locationCommandService.handle(new CreateLocationCommand(command.locationInformation()))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Location could not be created"));

        var membership = membershipCommandService.handle(new CreateMembershipCommand(command.membershipDescription(),
                command.started(), command.over()))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership could not be created"));

        var user = userCommandService.handle(new CreateUserCommand(command.name(), command.lastName(),
                command.dni(), command.phoneNumber(), location.getId()))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User could not be created"));

        var userAccount = new UserAccount(new CreateUserAccountCommand(command.username(), command.email(),
                role.getId(), user.getId(), membership.getId(), hashingService.encode(command.password())), role, user, membership);

        try {
            userAccountRepository.save(userAccount);
            return userAccountRepository.findByUsername(command.username());
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while saving User Account: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the creation of a new user account based on the provided command.
     *
     * @param command the command containing the user account information
     * @return the ID of the newly created user account
     */
    @Override
    public Long handle(CreateUserAccountCommand command) {
        var username = command.username();
        var email = command.email();

        if (userAccountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Username already exists");
        }

        if (userAccountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Email already exists");
        }

        var role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found"));

        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User not found"));

        var membership = membershipRepository.findById(command.membershipId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership not found"));

        var userAccount = new UserAccount(command, role, user, membership);

        try {
            userAccountRepository.save(userAccount);
            return userAccount.getId();
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while saving User Account: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the update of a user account based on the provided command.
     *
     * @param command the command containing the updated user account information
     * @return an optional of the updated user account
     */
    @Override
    public Optional<UserAccount> handle(UpdateUserAccountCommand command) {
        var userAccountId = command.userAccountId();
        var username = command.username();
        var email = command.email();

        if (!userAccountRepository.existsById(userAccountId)) {
            throw new NotFoundIdException(UserAccount.class, command.userAccountId());
        }

        if (userAccountRepository.existsByUsernameAndIdIsNot(username, userAccountId)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Username already exists");
        }

        if (userAccountRepository.existsByEmailAndIdIsNot(email, userAccountId)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Email already exists");
        }

        var userAccountToUpdate = userAccountRepository.findById(userAccountId).get();

        var role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found"));

        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User not found"));

        var membership = membershipRepository.findById(command.membershipId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership not found"));

        userAccountToUpdate.updateUserAccount(command, role, user, membership);

        try {
            var updatedUserAccount = userAccountRepository.save(userAccountToUpdate);
            return Optional.of(updatedUserAccount);
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while updating User Account: "
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
        if (!userAccountRepository.existsById(command.userAccountId())) {
            throw new NotFoundIdException(UserAccount.class, command.userAccountId());
        }
        try {
            userAccountRepository.deleteById(command.userAccountId());
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while deleting User Account: "
                    + e.getMessage());
        }
    }
}
