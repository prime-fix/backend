package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * UpdateUserAccountRequest
 *
 * @param username the user account's new username to be updated
 * @param email the email address associated with the user account to be updated
 * @param idRole the role identifier assigned to the user account to be updated
 * @param idUser the user identifier linked to the user account to be updated
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

        @JsonProperty("id_role")
        @NotNull @NotBlank
        String idRole,

        @JsonProperty("id_user")
        @NotNull @NotBlank
        String idUser,

        @NotNull @NotBlank
        @Size(min = 1, max = 100)
        String password,

        @Schema(name="is_new", defaultValue="false")
        @JsonProperty("is_new")
        boolean isNew) {
}
