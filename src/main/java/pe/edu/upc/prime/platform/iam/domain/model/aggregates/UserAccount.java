package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserAccountCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "user_accounts")
public class UserAccount extends AuditableAbstractAggregateRoot<UserAccount> {

    @Id
    @Getter
    @Column(name="id_user_account", nullable = false, unique = true)
    @JsonProperty("id_user_account")
    private String idUserAccount;

    @Getter
    @Column(name = "username", nullable = false, length = 150)
    private String username;

    @Getter
    @Column(name = "email", nullable = false, length = 200)
    private String email;


    @Getter
    @Column(name="id_role", nullable = false)
    @JsonProperty("id_role")
    private String idRole;

    @Getter
    @Column(name="id_user", nullable = false)
    @JsonProperty("id_role")
    private String idUser;

    @Getter
    @Column(name = "password", nullable = false, length = 100)
    private String password;


    @Getter
    @Column(name = "is_new", nullable = false)
    @JsonProperty("is_new")
    private boolean isNew;

    /**
     * Default constructor for JPA.
     */
    public UserAccount() {
    }

    public UserAccount(CreateUserAccountCommand command) {
        this.idUserAccount = command.idUserAccount();
        this.username = command.username();
        this.email = command.email();
        this.idRole = command.idRole();
        this.idUser = command.idUser();
        this.password = command.password();
        this.isNew = command.isNew();
    }


    public void updateUserAccount(UpdateUserAccountCommand command) {
        this.username = command.username();
        this.email = command.email();
        this.idRole = command.idRole();
        this.idUser = command.idUser();
        this.password = command.password();
        this.isNew = command.isNew();
    }

}
