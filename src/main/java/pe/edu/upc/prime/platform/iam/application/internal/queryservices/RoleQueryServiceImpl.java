package pe.edu.upc.prime.platform.iam.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Role;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetRoleByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.RoleQueryService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the RoleQueryService interface.
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {
    /**
     * * The repository to access role data.
     */
    private final RoleRepository roleRepository;

    /**
     * Constructor for RoleQueryServiceImpl.
     *
     * @param roleRepository the repository to access role data
     */
    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Get all roles.
     *
     * @param query the query to get all roles
     * @return a list of all roles
     */
    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return this.roleRepository.findAll();
    }

    /**
     * Get a role by its ID.
     *
     * @param query the query containing the role ID
     * @return an optional containing the role if found
     */
    @Override
    public Optional<Role> handle(GetRoleByIdQuery query) {
        return Optional.ofNullable(this.roleRepository.findById(query.idRole())
        .orElseThrow(() -> new NotFoundIdException(Role.class, query.idRole())));
    }
}
