package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request to update a user account.
 *
 * @param username the user account's new username to be updated
 * @param email the email address associated with the user account to be updated
 * @param roleId the role identifier assigned to the user account to be updated
 * @param userId the user identifier linked to the user account to be updated
 * @param membershipId the membership identifier associated with the user account to be updated
 * @param password the new password for the user account to be updated
 * @param isNew flag indicating if the account is new
 */
public record UpdateUserAccountRequest(
        @NotNull @NotBlank
        @Size(min = 1, max = 150)
        String username,

        @NotNull @NotBlank
        @Email(message = "Invalid email format")
        @Size(min = 1, max = 200)
        String email,

        @JsonProperty("role_id")
        @NotNull
        Long roleId,

        @JsonProperty("user_id")
        @NotNull
        Long userId,

        @JsonProperty("membership_id")
        @NotNull
        Long membershipId,

        @NotNull @NotBlank
        @Size(min = 1, max = 100)
        String password,

        @JsonProperty("is_new")
        @NotNull
        Boolean isNew) {
}

