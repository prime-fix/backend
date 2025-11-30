package pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreatePaymentRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.PaymentResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdatePaymentRequest;

public class PaymentAssembler {

    public static CreatePaymentCommand toCommandFromRequest(CreatePaymentRequest request) {
        return new CreatePaymentCommand(
                request.cardNumber(),
                CardType.valueOf(request.cardType()),
                request.month(),
                request.year(),
                request.ccv(),
                new UserAccountId(request.userAccountId())
        );
    }

    public static UpdatePaymentCommand toCommandFromRequest(Long paymentId, UpdatePaymentRequest request) {
        return new UpdatePaymentCommand(
                paymentId,
                request.cardNumber(),
                CardType.valueOf(request.cardType()),
                request.month(),
                request.year(),
                request.ccv(),
                new UserAccountId(request.userAccountId())
        );
    }

    public static PaymentResponse toResponseFromEntity(Payment entity) {
        return new PaymentResponse(
                entity.getId().toString(),
                entity.getMaskedCardNumber(),
                entity.getCardType().name(),
                entity.getMonth(),
                entity.getYear(),
                entity.getUserAccountId().value()
        );
    }
}
