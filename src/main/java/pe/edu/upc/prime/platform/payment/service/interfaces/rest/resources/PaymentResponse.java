package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resource representation of a Payment.
 *
 * @param id            The unique identifier of the payment.
 * @param cardNumber    The card number used for the payment (masked if needed).
 * @param cardType      The type of card.
 * @param month         The expiration month of the card.
 * @param year          The expiration year of the card.
 * @param userAccountId The ID of the user account linked to the payment.
 */
public record PaymentResponse(
        Long id,
        @JsonProperty("card_number") String cardNumber,
        @JsonProperty("card_type") String cardType,
        Integer month,
        Integer year,
        @JsonProperty("user_account_id") Long userAccountId) {
}
