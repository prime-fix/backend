package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleDescription;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleName;

import java.util.Objects;

/**
 * Command to update an existing role.
 *
 * @param idRole the identifier of the role to be updated
 * @param roleName the new name of the role to be updated
 * @param roleDescription the new description of the role to be updated
 */
public record UpdateRoleCommand(String idRole, RoleName roleName, RoleDescription roleDescription) {
    public UpdateRoleCommand {
        Objects.requireNonNull(idRole, "[UpdateRoleCommand] id role must not be null");
        Objects.requireNonNull(roleName, "[UpdateRoleCommand] role name must not be null");
        Objects.requireNonNull(roleDescription, "[UpdateRoleCommand] role description must not be null");
    }
}
