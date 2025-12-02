package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling role-related queries.
 */
public interface RoleQueryService {
    /**
     * Handle the query to get all roles.
     *
     * @param query the query to get all roles
     * @return a list of all roles
     */
    List<Role> handle(GetAllRolesQuery query);

    /**
     * Handle the query to get a role by its name.
     *
     * @param query the query to get a role by name
     * @return an Optional containing the Role if found, or empty if not found
     */
    Optional<Role> handle(GetRoleByNameQuery query);
}
