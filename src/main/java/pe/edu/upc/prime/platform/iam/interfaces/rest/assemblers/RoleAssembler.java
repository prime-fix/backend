package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Role;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleDescription;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleName;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateRoleRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.RoleResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateRoleRequest;

/**
 * Assembler for Role-related conversions between REST requests/responses and domain commands/entities.
 */
public class RoleAssembler {
    /**
     * Converts a CreateRoleRequest to a CreateRoleCommand.
     *
     * @param request the CreateRoleRequest object
     * @return the corresponding CreateRoleCommand object
     */
    public static CreateRoleCommand toCommandFromRequest(CreateRoleRequest request) {
        return new CreateRoleCommand(
                new RoleName(request.name()), new RoleDescription(request.description()));
    }

    /**
     * Converts an UpdateRoleRequest to an UpdateRoleCommand.
     *
     * @param idRole the ID of the role to be updated
     * @param request the UpdateRoleRequest object
     * @return the corresponding UpdateRoleCommand object
     */
    public static UpdateRoleCommand toCommandFromRequest(String idRole, UpdateRoleRequest request) {
        return new UpdateRoleCommand(idRole,
                new RoleName(request.name()), new RoleDescription(request.description()));
    }

    /**
     * Converts a Role entity to a RoleResponse.
     *
     * @param entity the Role entity
     * @return the corresponding RoleResponse object
     */
    public static RoleResponse toResponseFromEntity(Role entity) {
        return new RoleResponse(entity.getId().toString(), entity.getRoleName().name(), entity.getRoleDescription().description());
    }
}
