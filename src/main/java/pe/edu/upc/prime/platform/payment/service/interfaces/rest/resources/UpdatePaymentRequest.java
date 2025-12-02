package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


/**
 * Request to update an existing Payment.
 *
 * @param cardNumber    The new card number.
 * @param cardType      The new card type.
 * @param month         The expiration month.
 * @param year          The expiration year.
 * @param ccv           The new CCV.
 * @param userAccountId The user account ID linked to this payment.
 */
public record UpdatePaymentRequest(
        @JsonProperty("card_number")
        @NotNull @NotBlank
        @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits")
        String cardNumber,

        @JsonProperty("card_type")
        @NotNull
        @Pattern(regexp = "VISA|MASTERCARD|AMEX", message = "Card type must be VISA, MASTERCARD or AMEX")
        String cardType,

        @NotNull
        @Min(1) @Max(12)
        Integer month,

        @NotNull
        @Min(2000)
        Integer year,

        @NotNull
        @Min(100) @Max(9999)
        Integer ccv,

        @JsonProperty("user_account_id")
        @NotNull
        Long userAccountId) {
}
