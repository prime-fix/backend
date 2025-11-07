package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;

import java.util.Optional;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Check if a user exists by ID.
     *
     * @param userId the ID of the user
     * @return true if a user with the given ID exists, false otherwise
     */
    boolean existsByIdUser(String userId);

    /**
     * Check if a user exists by full name.
     *
     * @param name the first name of the user
     * @param lastName the last name of the user
     * @return true if a user with the given full name exists, false otherwise
     */
    boolean existsByNameAndLastName(String name, String lastName);

    /**
     * Check if a user exists by full name excluding a specific user ID.
     *
     * @param name the first name of the user
     * @param lastName the last name of the user
     * @return true if a user with the given full name exists excluding the specified user ID, false otherwise
     */
    boolean existsByNameAndLastNameAndIdUserIsNot(String name, String lastName, String idUser);

    /**
     * Find a user by full name.
     *
     * @param name the first name of the user
     * @param lastName the last name of the user
     * @return an optional user matching the given full name
     */
    Optional<User> findByNameAndLastName(String name, String lastName);
}
