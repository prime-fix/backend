package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;

import java.util.Optional;

/**
 * Repository interface for UserAccount entities.
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    /**
     * Check if a user account exists by its username.
     *
     * @param username the username of the user account
     * @return true if the user account exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user account exists by its email.
     *
     * @param email the email of the user account
     * @return true if the user account exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user account exists by its username excluding a specific user account ID.
     *
     * @param username      the username of the user account
     * @param idUserAccount the ID of the user account to exclude
     * @return true if the user account exists, false otherwise
     */
    boolean existsByUsernameAndIdIsNot(String username, Long idUserAccount);

    /**
     * Check if a user account exists by its email excluding a specific user account ID.
     *
     * @param email         the email of the user account
     * @param idUserAccount the ID of the user account to exclude
     * @return true if the user account exists, false otherwise
     */
    boolean existsByEmailAndIdIsNot(String email, Long idUserAccount);

    /**
     * Find a user account by its username.
     *
     * @param username the username of the user account
     * @return an Optional containing the UserAccount if found, or empty if not found
     */
    Optional<UserAccount> findByUsername(String username);

    /**
     * Find a user account by the associated user ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the UserAccount if found, or empty if not found
     */
    Optional<UserAccount> findByUserId(Long userId);
}
