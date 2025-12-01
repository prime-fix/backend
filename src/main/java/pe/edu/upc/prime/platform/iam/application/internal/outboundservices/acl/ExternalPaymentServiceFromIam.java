package pe.edu.upc.prime.platform.iam.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;
import pe.edu.upc.prime.platform.payment.service.interfaces.acl.PaymentServiceFacade;

/**
 * Service to interact with the Payment Service from the IAM context.
 */
@Service
public class ExternalPaymentServiceFromIam {
    /**
     *
     */
    private final PaymentServiceFacade paymentServiceFacade;

    /**
     * Constructor for ExternalPaymentServiceFromIam.
     *
     * @param paymentServiceFacade the payment service facade
     */
    public ExternalPaymentServiceFromIam(PaymentServiceFacade paymentServiceFacade) {
        this.paymentServiceFacade = paymentServiceFacade;
    }

    /**
     * Check if a payment exists by its ID.
     *
     * @param paymentId the payment ID
     * @return true if the payment exists, false otherwise
     */
    public boolean existsPaymentById(Long paymentId) {
        return this.paymentServiceFacade.existsPaymentById(paymentId);
    }

    /**
     * Create a new payment method.
     *
     * @param cardNumber the card number
     * @param cardType the card type
     * @param month the expiration month
     * @param year the expiration year
     * @param ccv the card verification value
     * @param userAccountId the user account ID
     * @return the ID of the created payment method
     */
    public Long createPayment(String cardNumber, CardType cardType,
                              Integer month, Integer year,
                              Integer ccv, Long userAccountId) {
        return this.paymentServiceFacade.createPayment(
                cardNumber, cardType, month, year, ccv, userAccountId
        );
    }
}
