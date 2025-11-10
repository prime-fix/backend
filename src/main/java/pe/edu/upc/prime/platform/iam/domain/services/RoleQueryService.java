package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Role;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetRoleByIdQuery;

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
     * Handle the query to get a role by its ID.
     *
     * @param query the query containing the role ID
     * @return an optional role matching the ID
     */
    Optional<Role> handle(GetRoleByIdQuery query);
}
