package pe.edu.upc.prime.platform.iam.domain.model.commands;

import java.util.Objects;

/**
 * Command to create a user account.
 *
 * @param idUserAccount the identifier of the user account
 * @param username the username of the user account
 * @param email the email of the user account
 * @param idRole the identifier of the role associated with the user account
 * @param idUser the identifier of the user associated with the user account
 * @param password the password of the user account
 * @param isNew indicates if the user account is new
 */
public record CreateUserAccountCommand(String idUserAccount, String username, String email, String idRole, String idUser, String password, boolean isNew) {

    public CreateUserAccountCommand {
        Objects.requireNonNull(idUserAccount, "[CreateUserAccountCommand] idUserAccount must not be null");
        Objects.requireNonNull(username, "[CreateUserAccountCommand] username must not be null");
        Objects.requireNonNull(email, "[CreateUserAccountCommand] email must not be null");
        Objects.requireNonNull(idRole, "[CreateUserAccountCommand] idRole must not be null");
        Objects.requireNonNull(idUser, "[CreateUserAccountCommand] idUser must not be null");
        Objects.requireNonNull(password, "[CreateUserAccountCommand] password must not be null");
    }
}
