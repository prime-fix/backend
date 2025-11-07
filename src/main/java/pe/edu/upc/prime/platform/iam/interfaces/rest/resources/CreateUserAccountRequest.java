package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * CreateUserAccountRequest
 *
 * @param idUserAccount the identifier of the user account to be created
 * @param username the username for the new user account
 * @param email the email address associated with the new user account
 * @param idRole the role identifier assigned to the new user account
 * @param idUser the user identifier linked to the new user account
 * @param password the password for the new user account
 * @param isNew flag indicating if the account is new
 */
public record CreateUserAccountRequest(
        @JsonProperty("id_user_account")
        @NotNull @NotBlank
        String idUserAccount,

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
