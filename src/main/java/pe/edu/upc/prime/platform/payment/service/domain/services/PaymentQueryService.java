package pe.edu.upc.prime.platform.payment.service.domain.services;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

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
}
