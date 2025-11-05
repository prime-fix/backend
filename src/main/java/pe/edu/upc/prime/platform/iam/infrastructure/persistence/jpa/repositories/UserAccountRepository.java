package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,String> {
    /**
     * Check if a user account exists by its ID.
     *
     * @param idUserAccount the ID of the user account
     * @return true if the user account exists, false otherwise
     */
    boolean existsByIdUserAccount(String idUserAccount);

    /**
     * Check if a user account exists by its username.
     *
     * @param username the username of the user account
     * @return true if the user account exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user account exists by its username excluding a specific user account ID.
     *
     * @param username the username of the user account
     * @param idUserAccount the ID of the user account to exclude
     * @return true if the user account exists, false otherwise
     */
    boolean existsByUsernameAndIdUserAccountIsNot(String username, String idUserAccount);

    /**
     * Check if a user account exists by its email excluding a specific user account ID.
     *
     * @param email the email of the user account
     * @param idUserAccount the ID of the user account to exclude
     * @return true if the user account exists, false otherwise
     */
    boolean existsByEmailAndIdUserAccountIsNot(String email, String idUserAccount);

    /**
     * Find a user account by its username.
     *
     * @param username the username of the user account
     * @return an Optional containing the user account if found, or empty if not found
     */
    Optional<UserAccount> findByUsername(String username);

    /**
     * Find a user account by its ID.
     *
     * @param idUser the ID of the user account
     * @return an Optional containing the user account if found, or empty if not found
     */
    Optional<UserAccount> findByIdUserAccount(String idUser);
}
