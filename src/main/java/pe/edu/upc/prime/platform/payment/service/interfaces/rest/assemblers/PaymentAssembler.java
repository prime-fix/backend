package pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers;

import jakarta.persistence.criteria.CriteriaBuilder;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreatePaymentRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.PaymentResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdatePaymentRequest;

/**
 * Assembler class for converting between Payment-related requests, commands, and responses.
 */
public class PaymentAssembler {
    /**
     * Convert CreatePaymentRequest to CreatePaymentCommand.
     *
     * @param request The create payment request.
     * @return The corresponding to create payment command.
     */
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

    /**
     * Convert UpdatePaymentRequest to UpdatePaymentCommand.
     *
     * @param paymentId The ID of the payment to update.
     * @param request The update payment request.
     * @return The corresponding to update payment command.
     */
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

    /**
     * Convert Payment entity to PaymentResponse.
     *
     * @param entity The payment entity.
     * @return The corresponding payment response.
     */
    public static PaymentResponse toResponseFromEntity(Payment entity) {
        return new PaymentResponse(
                entity.getId(),
                entity.getMaskedCardNumber(),
                entity.getCardType().name(),
                entity.getMonth(),
                entity.getYear(),
                entity.getUserAccountId().value()
        );
    }

    /**
     * Convert raw values to CreatePaymentCommand.
     *
     * @param cardNumber the card number
     * @param cardType the card type
     * @param month the expiration month
     * @param year the expiration year
     * @param ccv the card ccv
     * @param userAccountId the user account id
     * @return the corresponding to create payment command
     */
    public static CreatePaymentCommand toCommandFromValues(String cardNumber, CardType cardType,
                                                           Integer month, Integer year,
                                                           Integer ccv, Long userAccountId) {
        return new CreatePaymentCommand(cardNumber, cardType,
                month, year, ccv, new UserAccountId(userAccountId));
    }
}
