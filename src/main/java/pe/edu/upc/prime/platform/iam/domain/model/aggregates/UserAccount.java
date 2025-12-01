package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * UserAccount aggregate root entity.
 */
@Entity
@Table(name = "user_accounts")
public class UserAccount extends AuditableAbstractAggregateRoot<UserAccount> {

    @Getter
    @Column(name = "username", nullable = false, length = 150)
    private String username;

    @Getter
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Getter
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Getter
    @Column(name = "password", nullable = false, length = 100)
    private String password;


    @Getter
    @Column(name = "is_new", nullable = false)
    private Boolean isNew;

    /**
     * Default constructor for JPA.
     */
    public UserAccount() {
    }


    /**
     * Constructor to create a UserAccount from a command.
     *
     * @param command the command containing user account details
     */
    public UserAccount(CreateUserAccountCommand command, Role role, User user, Membership membership) {
        this.username = command.username();
        this.email = command.email();
        this.role = role;
        this.user = user;
        this.membership = membership;
        this.password = command.password();
        this.isNew = true;
    }


    /**
     * Updates the UserAccount with details from the command.
     *
     * @param command the command containing updated user account details
     */
    public void updateUserAccount(UpdateUserAccountCommand command, Role role, User user, Membership membership) {
        this.username = command.username();
        this.email = command.email();
        this.role = role;
        this.user = user;
        this.membership = membership;
        this.password = command.password();
        this.isNew = command.isNew();
    }
}
