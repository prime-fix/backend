package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.prime.platform.iam.application.internal.outboundservices.acl.ExternalAutoRepairCatalogServiceFromIam;
import pe.edu.upc.prime.platform.iam.application.internal.outboundservices.acl.ExternalPaymentServiceFromIam;
import pe.edu.upc.prime.platform.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.prime.platform.iam.application.internal.outboundservices.tokens.TokenService;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.*;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.prime.platform.iam.domain.services.*;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.*;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the UserAccountCommandService interface for handling user account commands.
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
     * The location repository for accessing location data.
     */
    private final LocationRepository locationRepository;

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
     * The external payment service for payment-related operations.
     */
    private final ExternalPaymentServiceFromIam externalPaymentServiceFromIam;

    /**
     * The external auto repair catalog service for interacting with the auto repair catalog.
     */
    private final ExternalAutoRepairCatalogServiceFromIam externalAutoRepairCatalogServiceFromIam;

    /**
     * Constructor for UserAccountCommandServiceImpl.
     *
     * @param userAccountRepository the user account repository
     * @param locationCommandService the location command service
     * @param locationRepository the location repository
     * @param membershipCommandService the membership command service
     * @param membershipRepository the membership repository
     * @param userCommandService the user command service
     * @param userRepository the user repository
     * @param hashingService the hashing service
     * @param tokenService the token service
     * @param roleRepository the role repository
     * @param externalPaymentServiceFromIam the external payment service
     * @param externalAutoRepairCatalogServiceFromIam the external auto repair catalog service
     */
    public UserAccountCommandServiceImpl(UserAccountRepository userAccountRepository,
                                         LocationCommandService locationCommandService,
                                         LocationRepository locationRepository,
                                         MembershipCommandService membershipCommandService,
                                         MembershipRepository membershipRepository,
                                         UserCommandService userCommandService,
                                         UserRepository userRepository,
                                         HashingService hashingService,
                                         TokenService tokenService,
                                         RoleRepository roleRepository,
                                         ExternalPaymentServiceFromIam externalPaymentServiceFromIam,
                                         ExternalAutoRepairCatalogServiceFromIam externalAutoRepairCatalogServiceFromIam) {
        this.userAccountRepository = userAccountRepository;
        this.locationCommandService = locationCommandService;
        this.locationRepository = locationRepository;
        this.membershipCommandService = membershipCommandService;
        this.membershipRepository = membershipRepository;
        this.userCommandService = userCommandService;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.externalPaymentServiceFromIam = externalPaymentServiceFromIam;
        this.externalAutoRepairCatalogServiceFromIam = externalAutoRepairCatalogServiceFromIam;
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
        // Check if user account exists
        if (userAccount.isEmpty()) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] User Account not found");
        }

        // Validate password
        if (!hashingService.matches(command.password(), userAccount.get().getPassword())) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Invalid password");
        }

        // Generate token
        var token = tokenService.generateToken(userAccount.get().getUsername());
        return Optional.of(ImmutablePair.of(userAccount.get(), token));
    }

    /**
     * Handles the vehicle owner sign-up command by creating a new user account for a vehicle owner.
     *
     * @param command the command containing the user account information
     * @return an optional of the newly created user account
     */
    @Transactional
    @Override
    public Optional<UserAccount> handle(VehicleOwnerSignUpCommand command) {
        // Define role name for vehicle owner
        var roleName = Roles.ROLE_VEHICLE_OWNER;

        // Check if username already exists
        if (userAccountRepository.existsByUsername(command.username())) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Username already exists");
        }

        // Check if email already exists
        if (!roleRepository.existsByName(roleName)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found");
        }

        // Find role entity
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found"));

        // Create location entity and retrieve it
        var location = locationRepository.findById(
                locationCommandService.handle(new CreateLocationCommand(command.locationInformation())))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Location could not be created"));

        // Create membership entity and retrieve it
        var membership = membershipRepository.findById(
                membershipCommandService.handle(new CreateMembershipCommand(command.membershipDescription(),
                        command.started(), command.over())))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership could not be created"));

        // Create user entity and retrieve it
        var user = userRepository.findById(userCommandService.handle(new CreateUserCommand(command.name(), command.lastName(),
                command.dni(), command.phoneNumber(), location.getId())))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User could not be created"));

        // Create user account entity
        var userAccount = new UserAccount(new CreateUserAccountCommand(command.username(), command.email(),
                role.getId(), user.getId(), membership.getId(), hashingService.encode(command.password())), role, user, membership);


        try {
            // Save user account entity to obtain its ID and validate from Payment Service its creation
            userAccountRepository.save(userAccount);

            // Create payment entity via external service
            var paymentId = externalPaymentServiceFromIam.createPayment(command.cardNumber(), command.cardType(),
                    command.month(), command.year(), command.cvv(),
                    userAccount.getId());

            // Validate payment creation
            if (paymentId.equals(0L)) {
                throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Payment could not be created");
            }

            // Return the created user account
            return userAccountRepository.findByUsername(command.username());
        } catch (Exception e) {
            throw new PersistenceException("[UserAccountCommandServiceImpl] Error while saving User Account: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the auto repair sign-up command by creating a new user account for an auto repair.
     *
     * @param command the command containing the auto repair user account information
     * @return an optional of the newly created user account
     */
    @Transactional
    @Override
    public Optional<UserAccount> handle(AutoRepairSignUpCommand command) {
        // Define role name for auto repair
        var roleName = Roles.ROLE_AUTO_REPAIR;

        // Check if username already exists
        if (userAccountRepository.existsByUsername(command.username())) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Username already exists.");
        }

        // Check if email already exists
        if (!roleRepository.existsByName(roleName)) {
            throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found.");
        }

        // Find role entity
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found."));

        // Create location entity and retrieve it
        var location = locationRepository.findById(
                        locationCommandService.handle(new CreateLocationCommand(command.locationInformation())))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Location could not be created."));

        // Create membership entity and retrieve it
        var membership = membershipRepository.findById(
                        membershipCommandService.handle(new CreateMembershipCommand(command.membershipDescription(),
                                command.started(), command.over())))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership could not be created."));

        // Create user entity and retrieve it
        var user = userRepository.findById(userCommandService.handle(new CreateUserCommand(command.name(), command.lastName(),
                        command.dni(), command.phoneNumber(), location.getId())))
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User could not be created."));

        // Create user account entity
        var userAccount = new UserAccount(new CreateUserAccountCommand(command.username(), command.email(),
                role.getId(), user.getId(), membership.getId(), hashingService.encode(command.password())), role, user, membership);


        try {
            // Save user account entity to obtain its ID and validate from Payment Service its creation
            userAccountRepository.save(userAccount);

            // Create payment entity via external service
            var paymentId = this.externalPaymentServiceFromIam.createPayment(command.cardNumber(), command.cardType(),
                    command.month(), command.year(), command.cvv(),
                    userAccount.getId());

            // Validate payment creation
            if (paymentId.equals(0L)) {
                throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Payment could not be created");
            }

            // Create auto repair entity via external service
            var autoRepairId = this.externalAutoRepairCatalogServiceFromIam.createAutoRepair(command.contactEmail(),
                    command.ruc(), userAccount.getId());

            // Validate auto repair creation
            if (autoRepairId.equals(0L)) {
                throw new IllegalArgumentException("[UserAccountCommandServiceImpl] Auto Repair could not be created");
            }

            // Return the created user account
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

        // Retrieve role entity
        var role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found"));

        // Retrieve user entity
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User not found"));

        // Retrieve membership entity
        var membership = membershipRepository.findById(command.membershipId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership not found"));

        // Create user account entity
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

        // Retrieve role entity
        var role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Role not found"));

        // Retrieve user entity
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] User not found"));

        // Retrieve membership entity
        var membership = membershipRepository.findById(command.membershipId())
                .orElseThrow(() -> new IllegalArgumentException("[UserAccountCommandServiceImpl] Membership not found"));

        // Update user account entity
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
