package pe.edu.upc.prime.platform.iam.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;
import pe.edu.upc.prime.platform.payment.service.interfaces.acl.PaymentServiceFacade;

@Service
public class ExternalPaymentServiceFromIam {
    private final PaymentServiceFacade paymentServiceFacade;

    public ExternalPaymentServiceFromIam(PaymentServiceFacade paymentServiceFacade) {
        this.paymentServiceFacade = paymentServiceFacade;
    }

    public boolean existsPaymentById(Long paymentId) {
        return this.paymentServiceFacade.existsPaymentById(paymentId);
    }

    public Long createPayment(String cardNumber, CardType cardType,
                              Integer month, Integer year,
                              Integer ccv, Long userAccountId) {
        return this.paymentServiceFacade.createPayment(
                cardNumber, cardType, month, year, ccv, userAccountId
        );
    }
}
