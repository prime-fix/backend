package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

/**
 * Request to create a new Payment.
 *
 * @param cardNumber    The card number used for the payment.
 * @param cardType      The type of card (VISA, MASTERCARD, AMEX).
 * @param month         The expiration month of the card.
 * @param year          The expiration year of the card.
 * @param ccv           The security code of the card.
 * @param idUserAccount The ID of the user account linked to the payment.
 */
public record CreatePaymentRequest(

        @JsonProperty("card_number")
        @NotNull @NotBlank
        @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits")
        String cardNumber,

        @JsonProperty("card_type")
        @NotNull
        @Pattern(regexp = "VISA|MASTERCARD|AMEX", message = "Card type must be VISA, MASTERCARD or AMEX")
        String cardType,

        @JsonProperty("month")
        @Min(1) @Max(12)
        int month,

        @JsonProperty("year")
        @Min(2000)
        int year,

        @JsonProperty("ccv")
        @NotNull @Pattern(regexp = "\\d{3}", message = "CCV must have exactly 3 digits")
        String ccv,

        @JsonProperty("id_user_account")
        @NotNull @NotBlank
        String idUserAccount
) {

}
