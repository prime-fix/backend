package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.Objects;

/**
 * Command to update an existing Payment.
 *
 * @param paymentId The ID of the payment to update.
 * @param cardNumber The new card number.
 * @param cardType The new type of the card.
 * @param month The new expiration month.
 * @param year The new expiration year.
 * @param ccv The new CCV code.
 * @param idUserAccount The user account associated with this payment.
 */

public record UpdatePaymentCommand(
        Long paymentId,
        String cardNumber,
        CardType cardType,
        int month,
        int year,
        String ccv,
        UserAccountId idUserAccount
) {
    public UpdatePaymentCommand {
        Objects.requireNonNull(paymentId, "[UpdatePaymentCommand] paymentId must not be null");
        Objects.requireNonNull(cardNumber, "[UpdatePaymentCommand] cardNumber must not be null");
        Objects.requireNonNull(cardType, "[UpdatePaymentCommand] cardType must not be null");
        Objects.requireNonNull(ccv, "[UpdatePaymentCommand] ccv must not be null");
        Objects.requireNonNull(idUserAccount, "[UpdatePaymentCommand] idUserAccount must not be null");

        if (paymentId <= 0)
            throw new IllegalArgumentException("[UpdatePaymentCommand] paymentId cannot be less than or equal to zero");

        if (cardNumber.isBlank())
            throw new IllegalArgumentException("[UpdatePaymentCommand] cardNumber cannot be blank");
        if (cardNumber.length() < 13 || cardNumber.length() > 19)
            throw new IllegalArgumentException("[UpdatePaymentCommand] cardNumber length must be between 13 and 19 digits");

        if (month < 1 || month > 12)
            throw new IllegalArgumentException("[UpdatePaymentCommand] month must be between 1 and 12");
        if (year < 2000)
            throw new IllegalArgumentException("[UpdatePaymentCommand] year must be greater than 2000");

        if (!ccv.matches("^\\d{3}$"))
            throw new IllegalArgumentException("[UpdatePaymentCommand] ccv must have exactly 3 digits");
    }
}
