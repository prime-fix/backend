package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

/**
 * Query to get a payment by its ID.
 *
 * @param paymentId the ID of the profile to retrieve
 */
public record GetPaymentByIdQuery(String paymentId) {
}
