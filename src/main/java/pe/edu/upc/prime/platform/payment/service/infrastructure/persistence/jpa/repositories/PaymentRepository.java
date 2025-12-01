package pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Custom query method to check existence of a payment by userAccountId.
     *
     * @param userAccountId the user account id to check
     * @return true if a payment with the given userAccountId exists, false otherwise
     */
    boolean existsByUserAccountId(UserAccountId userAccountId);

    /**
     * Find payments by userAccountId.
     *
     * @param userAccountId the user account id to search for
     * @return a list of payments associated with the given userAccountId
     */
    List<Payment> findByUserAccountId(UserAccountId userAccountId);

}
