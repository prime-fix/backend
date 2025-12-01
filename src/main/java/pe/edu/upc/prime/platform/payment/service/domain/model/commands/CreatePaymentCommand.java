package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.Objects;

/**
 * Command to create a new Payment.
 *
 * @param cardNumber The card number used for payment.
 * @param cardType The type of the card (e.g. VISA, MASTERCARD).
 * @param month The expiration month of the card.
 * @param year The expiration year of the card.
 * @param ccv The CCV code of the card.
 * @param userAccountId The identifier of the user account making the payment.
 */
public record CreatePaymentCommand(

        String cardNumber,
        CardType cardType,
        int month,
        int year,
        String ccv,
        UserAccountId userAccountId
) {
    public CreatePaymentCommand {
        Objects.requireNonNull(cardNumber, "[CreatePaymentCommand] cardNumber must not be null");
        Objects.requireNonNull(cardType, "[CreatePaymentCommand] cardType must not be null");
        Objects.requireNonNull(ccv, "[CreatePaymentCommand] ccv must not be null");
        Objects.requireNonNull(userAccountId, "[CreatePaymentCommand] idUserAccount must not be null");

        if (cardNumber.isBlank())
            throw new IllegalArgumentException("[CreatePaymentCommand] cardNumber cannot be blank");
        if (cardNumber.length() < 13 || cardNumber.length() > 19)
            throw new IllegalArgumentException("[CreatePaymentCommand] cardNumber length must be between 13 and 19 digits");

        if (month < 1 || month > 12)
            throw new IllegalArgumentException("[CreatePaymentCommand] month must be between 1 and 12");
        if (year < 2000)
            throw new IllegalArgumentException("[CreatePaymentCommand] year must be greater than 2000");

        if (!ccv.matches("^\\d{3}$"))
            throw new IllegalArgumentException("[CreatePaymentCommand] ccv must have exactly 3 digits");
    }
}
