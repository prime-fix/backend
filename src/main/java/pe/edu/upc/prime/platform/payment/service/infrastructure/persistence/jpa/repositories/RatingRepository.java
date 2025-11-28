package pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdUserAccount;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /** Custom query method to check the existence of a rating by auto repair.
     *
     * @param idAutoRepair the rating to check for existence
     * @return true if a rating with the given auto repair ID exists, false otherwise
     */
    boolean existsByIdAutoRepair(IdAutoRepair idAutoRepair);

    /** Custom query method to check the existence of a rating by user account.
     *
     * @param idUserAccount the rating to check for existence
     * @return true if a rating with the given user account ID exists, false otherwise
     */
    boolean existsByIdUserAccount(IdUserAccount idUserAccount);

    /**
     * Check if a rating exists by its auto repair ID and user account ID.
     *
     * @param idAutoRepair the username of the auto repair
     * @param idUserAccount the ID of the user account to exclude
     * @return true if the user account exists, false otherwise
     */
    boolean existsByIdAutoRepairAndIdUserAccount(IdAutoRepair idAutoRepair, IdUserAccount idUserAccount);

    /** Custom query method to find a rating by idUserAccount.
     *
     * @param idUserAccount the ID of the user account to search for
     * @return a List containing the found Ratings if found, or empty if not found
     */
    List<Rating> findByIdUserAccount(IdUserAccount idUserAccount);

    /** Custom query method to find a rating by idAutoRepair.
     *
     * @param idAutoRepair the ID of the auto repair to search for
     * @return a List containing the found Ratings if found, or empty if not found
     */
    List<Rating> findByIdAutoRepair(IdAutoRepair idAutoRepair);


}
