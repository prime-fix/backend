package pe.edu.upc.prime.platform.payment.service.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeletePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllPaymentsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.PaymentAssembler;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.PaymentResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Facade for managing Payments — provides simplified access for other bounded contexts.
 */
@Service
public class PaymentsContextFacade {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    public PaymentsContextFacade(PaymentCommandService paymentCommandService,
                                 PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    /**
     * Retrieves all payments.
     */
    public List<PaymentResponse> fetchAllPayments() {
        var query = new GetAllPaymentsQuery();
        return paymentQueryService.handle(query)
                .stream()
                .map(PaymentAssembler::toResponseFromEntity)
                .toList();
    }


    /**
     * Fetches a payment by its unique ID.
     */
    public Optional<PaymentResponse> fetchPaymentById(String paymentId) {
        var query = new GetPaymentByIdQuery(paymentId);
        var optionalPayment = paymentQueryService.handle(query);
        return optionalPayment.map(PaymentAssembler::toResponseFromEntity);
    }

    /**
     * Creates a new payment.
     */
    public String createPayment(String cardNumber, String cardType, int month,
                                int year, String ccv, String idUserAccount) {
        var command = PaymentAssembler.toCommandFromRequest(
                new pe.edu.upc.center.bc_pay_rate.payment.service.interfaces.rest.resources.CreatePaymentRequest(
                        cardNumber, cardType, month, year, ccv, idUserAccount));
        var paymentId = paymentCommandService.handle(command);
        return Objects.requireNonNullElse(paymentId, "0");
    }

    /**
     * Updates an existing payment.
     */
    public String updatePayment(String id, String cardNumber, String cardType, int month,
                                int year, String ccv, String idUserAccount) {
        var command = PaymentAssembler.toCommandFromRequest(
                new pe.edu.upc.center.bc_pay_rate.payment.service.interfaces.rest.resources.UpdatePaymentRequest(
                        id, cardNumber, cardType, month, year, ccv, idUserAccount));
        var optionalPayment = paymentCommandService.handle(command);
        return optionalPayment.map(Payment::getId).orElse("0");
    }

    /**
     * Deletes a payment by its ID.
     */
    public void deletePayment(String paymentId) {
        var command = new DeletePaymentCommand(paymentId);
        paymentCommandService.handle(command);
    }
}
