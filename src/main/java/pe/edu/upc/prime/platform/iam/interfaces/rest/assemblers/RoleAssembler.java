package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.RoleResponse;

/**
 * Assembler for Role entity and RoleResponse
 */
public class RoleAssembler {
    /**
     * Converts a Role entity to a RoleResponse
     *
     * @param entity the Role entity to convert
     * @return RoleResponse
     */
    public static RoleResponse toResponseFromEntity(Role entity) {
        return new RoleResponse(
                entity.getId(),
                entity.getStringName()
        );
    }
}
