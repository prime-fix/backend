package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Role;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleDescription;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.RoleName;

/**
 * Repository interface for Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    /**
     * Check if a role exists by its name.
     *
     * @param roleName the name of the role
     * @return true if a role with the given name exists, false otherwise
     */
    boolean existsByRoleName(RoleName roleName);

    /**
     * Check if a role exists by its description.
     *
     * @param roleDescription the description of the role
     * @return true if a role with the given description exists, false otherwise
     */
    boolean existsByRoleDescription(RoleDescription roleDescription);

    /**
     * Check if a role exists by its name, excluding a specific role ID.
     *
     * @param roleName the name of the role
     * @param idRole the ID of the role to exclude
     * @return true if a role with the given name exists excluding the specified role ID, false otherwise
     */
    boolean existsByRoleNameAndIdRoleIsNot(RoleName roleName, String idRole);

    /**
     * Check if a role exists by its description, excluding a specific role ID.
     *
     * @param roleDescription the description of the role
     * @param idRole the ID of the role to exclude
     * @return true if a role with the given description exists excluding the specified role ID, false otherwise
     */
    boolean existsByRoleDescriptionAndIdRoleIsNot(RoleDescription roleDescription, String idRole);
}
