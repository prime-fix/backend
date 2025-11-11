package pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdUserAccount;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    /** Custom query method to check the existence of a payment method by a specific ID.
     *
     * @param idUserAccount the email to check for existence
     * @return true if a payment method with the given ID exists, false otherwise
     */
    boolean existsByIdUserAccount(IdUserAccount idUserAccount);


    /** Custom query method to find a payment by idUserAccount.
     *
     * @param idUserAccount the unique identifier to search for
     * @return a List containing the found Payments if found, or empty if not found
     */
    List<Payment> findByIdUserAccount(IdUserAccount idUserAccount);

}
