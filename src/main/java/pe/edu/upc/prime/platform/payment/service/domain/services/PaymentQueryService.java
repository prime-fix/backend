package pe.edu.upc.prime.platform.payment.service.domain.services;

import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling payment-related queries.
 */
public interface PaymentQueryService {
    /**
     * Retrieves all payments.
     */
    List<Payment> handle(GetAllPaymentsQuery query);

    /**
     * Retrieves a payment by its unique identifier.
     */
    Optional<Payment> handle(GetPaymentByIdQuery query);

    /**
     * Retrieves all payments belonging to a specific user account.
     */
    List<Payment> handle(GetPaymentByIdUserAccountQuery query);

    /**
     * Checks if a payment exists by its ID.
     *
     * @param query the query containing the payment ID to check
     * @return true if the payment exists, false otherwise
     */
    boolean handle(ExistsPaymentByIdQuery query);
}
