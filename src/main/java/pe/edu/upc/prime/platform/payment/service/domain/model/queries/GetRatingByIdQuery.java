package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

/**
 * Query to get a rating by its ID.
 *
 * @param ratingId the ID of the profile to retrieve
 */
public record GetRatingByIdQuery(Long ratingId) {
}
