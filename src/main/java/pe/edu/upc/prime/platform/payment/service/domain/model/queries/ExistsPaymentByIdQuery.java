package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

/**
 * Query to check if a payment exists by its ID.
 *
 * @param paymentId The ID of the payment to check.
 */
public record ExistsPaymentByIdQuery(Long paymentId) {
}
