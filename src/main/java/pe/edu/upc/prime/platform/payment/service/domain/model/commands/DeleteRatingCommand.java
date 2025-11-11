package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

/**
 * Command to delete a rating by its ID.
 *
 * @param ratingId the ID of the rating to be deleted
 */
public record DeleteRatingCommand(String ratingId) {
}
