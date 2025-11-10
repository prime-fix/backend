package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UserAccountResponse
 *
 * @param idUserAccount the identifier of the user account
 * @param username the username of the user account
 * @param email the email address associated with the user account
 * @param idRole the role identifier assigned to the user account
 * @param idUser the user identifier linked to the user account
 * @param password the password of the user account
 * @param isNew flag indicating if the account is new
 */
public record UserAccountResponse(
        @JsonProperty("id_user_account") String idUserAccount,
        String username,
        String email,
        @JsonProperty("id_role") String idRole,
        @JsonProperty("id_user") String idUser,
        String password,
        @JsonProperty("is_new") boolean isNew) {
}
