package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;

import java.util.Objects;

/**
 * Command to create a user account.
 *
 * @param username the username of the user account to be created
 * @param email the email of the user account to be created
 * @param roleId the role id associated with the user account to be created
 * @param userId the user id associated with the account to be created
 * @param membershipId the membership id associated with the user account to be created
 * @param password the password of the user account to be created
 */
public record CreateUserAccountCommand(String username, String email, Long roleId, Long userId, Long membershipId, String password) {

    /**
     * Constructor with null checks.
     *
     * @param username the username of the user account to be created
     * @param email the email of the user account to be created
     * @param roleId the role id associated with the user account to be created
     * @param userId the user id associated with the account to be created
     * @param membershipId the membership id associated with the user account to be created
     * @param password the password of the user account to be created
     */
    public CreateUserAccountCommand {
        Objects.requireNonNull(username, "[CreateUserAccountCommand] username must not be null");
        Objects.requireNonNull(email, "[CreateUserAccountCommand] email must not be null");
        Objects.requireNonNull(roleId, "[CreateUserAccountCommand] role id must not be null");
        Objects.requireNonNull(userId, "[CreateUserAccountCommand] user id must not be null");
        Objects.requireNonNull(membershipId, "[CreateUserAccountCommand] membership id must not be null");
        Objects.requireNonNull(password, "[CreateUserAccountCommand] password must not be null");
    }
}
