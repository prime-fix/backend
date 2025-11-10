package pe.edu.upc.prime.platform.iam.domain.model.commands;

import java.util.Objects;

/**
 * Command to update a user account.
 *
 * @param idUserAccount the identifier of the user account to be updated
 * @param username the username of the user account to be updated
 * @param email the email of the user account to be updated
 * @param idRole the identifier of the role associated with the user account to be updated
 * @param idUser the identifier of the user associated with the user account to be updated
 * @param password the password of the user account to be updated
 * @param isNew update indicator for the user account if it is new or not
 */
public record UpdateUserAccountCommand(String idUserAccount, String username, String email, String idRole, String idUser, String password, Boolean isNew) {

    public UpdateUserAccountCommand {
        Objects.requireNonNull(idUserAccount, "[UpdateUserAccountCommand] idUserAccount must not be null");
        Objects.requireNonNull(username, "[UpdateUserAccountCommand] username must not be null");
        Objects.requireNonNull(email, "[UpdateUserAccountCommand] email must not be null");
        Objects.requireNonNull(idRole, "[UpdateUserAccountCommand] idRole must not be null");
        Objects.requireNonNull(idUser, "[UpdateUserAccountCommand] idUser must not be null");
        Objects.requireNonNull(password, "[UpdateUserAccountCommand] password must not be null");
        Objects.requireNonNull(isNew, "[UpdateUserAccountCommand] isNew must not be null");
    }
}
