package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleDescription;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleName;

import java.util.Objects;

/**
 * Command to create a new role.
 *
 * @param idRole the identifier of the role to be created
 * @param roleName the name of the role to be created
 * @param roleDescription the description of the role to be created
 */
public record CreateRoleCommand(String idRole, RoleName roleName, RoleDescription roleDescription) {
    public CreateRoleCommand {
        Objects.requireNonNull(idRole, "[CreateRoleCommand] id role must not be null");
        Objects.requireNonNull(roleName, "[CreateRoleCommand] role name must not be null");
        Objects.requireNonNull(roleDescription, "[CreateRoleCommand] role description must not be null");
    }
}
