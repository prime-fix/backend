package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.Roles;

import java.util.Optional;

/**
 * Repository interface for Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find a role by its name.
     *
     * @param name the name of the role
     * @return an Optional containing the Role if found, or empty if not found
     */
    Optional<Role> findByName(Roles name);

    /**
     * Check if a role exists by its name.
     *
     * @param name the name of the role to be checked
     * @return true if a role with the given name exists, false otherwise
     */
    boolean existsByName(Roles name);
}
