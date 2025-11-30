package pe.edu.upc.prime.platform.payment.service.domain.services;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.*;

import java.util.Optional;

public interface PaymentCommandService {
    /**
     * Handles the creation of a new Payment.
     * @param command The command containing payment details.
     * @return The ID of the created Payment.
     */
    Long handle(CreatePaymentCommand command);

    /**
     * Handles updating an existing Payment.
     * @param command The command containing updated data.
     * @return The updated Payment, if found.
     */
    Optional<Payment> handle(UpdatePaymentCommand command);

    /**
     * Handles deleting an existing Payment.
     * @param command The command specifying which Payment to delete.
     */
    void handle(DeletePaymentCommand command);
}
