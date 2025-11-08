package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleDescription;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleName;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Role aggregate root representing a role in the IAM system.
 */
@Entity
@Table(name = "roles")
public class Role extends AuditableAbstractAggregateRoot<Role> {

    @Id
    @Getter
    @Column(name = "id_role", nullable = false, unique = true)
    @JsonProperty("id_role")
    private String idRole;

    @Getter
    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "roleName",
                    joinColumns = @JoinColumn(name = "name"))
    })
    private RoleName roleName;

    @Getter
    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "roleDescription",
                    joinColumns = @JoinColumn(name = "description"))
    })
    private RoleDescription roleDescription;

    /**
     * Default constructor for JPA.
     */
    public Role() {
    }

    /**
     * Constructor to create a Role from a CreateRoleCommand.
     *
     * @param command the command containing role details
     */
    public Role(CreateRoleCommand command) {
        this.idRole = command.idRole();
        this.roleName = command.roleName();
        this.roleDescription = command.roleDescription();
    }

    /**
     * Updates the Role with details from an UpdateRoleCommand.
     *
     * @param command the command containing updated role details
     */
    public void updateRole(UpdateRoleCommand command) {
        this.roleName = command.roleName();
        this.roleDescription = command.roleDescription();
    }

}
