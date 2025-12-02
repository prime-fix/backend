package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

/**
 * CreateUserAccountRequest
 *
 * @param username the username for the new user account
 * @param email the email address associated with the new user account
 * @param roleId the role identifier assigned to the new user account
 * @param userId the user identifier linked to the new user account
 * @param password the password for the new user account
 */
public record CreateUserAccountRequest(

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
        String password) {
}
