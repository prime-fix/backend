package pe.edu.upc.prime.platform.payment.service.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.ExistsPaymentByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.PaymentAssembler;

import java.util.Objects;

/**
 * Facade for payment operations, providing methods to manage payments.
 */
@Service
public class PaymentServiceFacade {
    /**
     * The payment command service for handling payment-related commands.
     */
    private final PaymentCommandService paymentCommandService;
    /**
     * The payment query service for handling payment-related queries.
     */
    private final PaymentQueryService paymentQueryService;

    /**
     * Constructs a PaymentServiceFacade with the specified command and query services.
     *
     * @param paymentCommandService the service for handling payment commands
     * @param paymentQueryService the service for handling payment queries
     */
    public PaymentServiceFacade(PaymentCommandService paymentCommandService,
                                PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    /**
     * Check if a payment exists by its ID.
     *
     * @param paymentId the ID of the payment to check
     * @return true if the payment exists, false otherwise
     */
    public boolean existsPaymentById(Long paymentId) {
        var existsPaymentByIdQuery = new ExistsPaymentByIdQuery(paymentId);
        return this.paymentQueryService.handle(existsPaymentByIdQuery);
    }


    /**
     * Create a new payment method for a user.
     *
     * @param cardNumber the card number
     * @param cardType the type of the card
     * @param month the expiration month
     * @param year the expiration year
     * @param ccv the card verification code
     * @param userAccountId the user account ID
     * @return the ID of the created payment method, or 0L if creation failed
     */
    public Long createPayment(String cardNumber, CardType cardType,
                              Integer month, Integer year,
                              Integer ccv, Long userAccountId) {
        var createPaymentCommand = PaymentAssembler.toCommandFromValues(
                cardNumber, cardType, month, year, ccv, userAccountId);

        var paymentId = this.paymentCommandService.handle(createPaymentCommand);
        if (Objects.isNull(paymentId)) {
            return 0L;
        }
        return paymentId;
    }
}
