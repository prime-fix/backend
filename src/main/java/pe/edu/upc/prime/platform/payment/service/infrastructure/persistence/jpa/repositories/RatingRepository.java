package pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.List;

/**
 * Repository interface for managing Rating entities.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Check if a rating exists for a given auto repair ID.
     *
     * @param autoRepairId the auto repair ID
     * @return true if a rating exists for the given auto repair ID, false otherwise
     */
    boolean existsByAutoRepairId(AutoRepairId autoRepairId);

    /**
     * Check if a rating exists for a given user account ID.
     *
     * @param userAccountId the user account ID
     * @return true if a rating exists for the given user account ID, false otherwise
     */
    boolean existsByUserAccountId(UserAccountId userAccountId);

    /**
     * Check if a rating exists for a given auto repair ID and user account ID.
     *
     * @param autoRepairId the auto repair ID
     * @param userAccountId the user account ID
     * @return true if a rating exists for the given auto repair ID and user account ID, false otherwise
     */
    boolean existsByAutoRepairIdAndUserAccountId(AutoRepairId autoRepairId, UserAccountId userAccountId);

    /**
     * Find ratings by user account ID.
     *
     * @param userAccountId the user account ID
     * @return list of ratings associated with the given user account ID
     */
    List<Rating> findByUserAccountId(UserAccountId userAccountId);

    /**
     * Find ratings by auto repair ID.
     *
     * @param autoRepairId the auto repair ID
     * @return list of ratings associated with the given auto repair ID
     */
    List<Rating> findByAutoRepairId(AutoRepairId autoRepairId);
}
