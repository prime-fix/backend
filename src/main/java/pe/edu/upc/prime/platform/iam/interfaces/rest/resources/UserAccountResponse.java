package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response representing a user account.
 *
 * @param id the identifier of the user account
 * @param username the username of the user account
 * @param email the email address associated with the user account
 * @param roleId the role identifier assigned to the user account
 * @param userId the user identifier linked to the user account
 * @param membershipId the membership identifier linked to the user account
 * @param password the password of the user account
 * @param isNew flag indicating if the account is new
 */
public record UserAccountResponse(
        Long id,
        String username,
        String email,
        @JsonProperty("role_id") Long roleId,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("membership_id") Long membershipId,
        String password,
        @JsonProperty("is_new") boolean isNew) {
}
