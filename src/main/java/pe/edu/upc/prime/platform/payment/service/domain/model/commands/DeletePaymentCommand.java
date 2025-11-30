package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

/**
 * Command to delete a payment by its ID.
 *
 * @param paymentId the ID of the payment to be deleted
 */
public record DeletePaymentCommand(Long paymentId) {
}
