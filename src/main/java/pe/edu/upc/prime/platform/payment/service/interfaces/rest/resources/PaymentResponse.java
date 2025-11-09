package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

/**
 * Resource representation of a Payment.
 *
 * @param id            The unique identifier of the payment.
 * @param cardNumber    The card number used for the payment (masked if needed).
 * @param cardType      The type of card.
 * @param month         The expiration month of the card.
 * @param year          The expiration year of the card.
 * @param idUserAccount The ID of the user account linked to the payment.
 */
public record PaymentResponse(
        String id,
        String cardNumber,
        String cardType,
        int month,
        int year,
        String idUserAccount
) {
}
