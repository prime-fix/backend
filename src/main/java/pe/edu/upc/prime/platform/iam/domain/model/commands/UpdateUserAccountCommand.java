package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;

import java.util.Objects;

/**
 * Command to update a user account.
 *
 * @param userAccountId the identifier of the user account to be updated
 * @param username the username of the user account to be updated
 * @param email the email of the user account to be updated
 * @param roleId the role id of the user account to be updated
 * @param userId the user id entity associated with the user account to be updated
 * @param membershipId the membership id entity associated with the user account to be updated
 * @param password the password of the user account to be updated
 * @param isNew update indicator for the user account if it is new or not
 */
public record UpdateUserAccountCommand(Long userAccountId ,String username, String email,
                                       Long roleId, Long userId, Long membershipId, String password, Boolean isNew) {

    /**
     * Constructor with null checks.
     *
     * @param userAccountId the identifier of the user account to be updated
     * @param username the username of the user account to be updated
     * @param email the email of the user account to be updated
     * @param roleId the role of the user account to be updated
     * @param userId the user entity associated with the user account to be updated
     * @param membershipId the membership entity associated with the user account to be updated
     * @param password the password of the user account to be updated
     * @param isNew the update indicator for the user account if it is new or not
     */
    public UpdateUserAccountCommand {
        Objects.requireNonNull(userAccountId, "[UpdateUserAccountCommand] userAccountId must not be null");
        Objects.requireNonNull(username, "[UpdateUserAccountCommand] username must not be null");
        Objects.requireNonNull(email, "[UpdateUserAccountCommand] email must not be null");
        Objects.requireNonNull(roleId, "[UpdateUserAccountCommand] role id must not be null");
        Objects.requireNonNull(userId, "[UpdateUserAccountCommand] user id must not be null");
        Objects.requireNonNull(membershipId, "[UpdateUserAccountCommand] membership id must not be null");
        Objects.requireNonNull(password, "[UpdateUserAccountCommand] password id must not be null");
        Objects.requireNonNull(isNew, "[UpdateUserAccountCommand] isNew must not be null");
    }
}
