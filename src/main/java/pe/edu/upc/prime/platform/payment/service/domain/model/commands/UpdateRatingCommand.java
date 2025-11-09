package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;

import java.util.Objects;

/**
 * Command to update an existing Rating.
 *
 * @param ratingId The ID of the rating to update.
 * @param starRating The new star rating (1–5).
 * @param comment The new comment.
 * @param idAutoRepair The auto repair associated with this rating.
 * @param idUserAccount The user account that made the rating.
 */

public record UpdateRatingCommand(
        String ratingId,
        int starRating,
        String comment,
        IdAutoRepair idAutoRepair,
        IdUserAccount idUserAccount
) {
    public UpdateRatingCommand {
        Objects.requireNonNull(ratingId, "[UpdateRatingCommand] ratingId must not be null");
        Objects.requireNonNull(idAutoRepair, "[UpdateRatingCommand] idAutoRepair must not be null");
        Objects.requireNonNull(idUserAccount, "[UpdateRatingCommand] idUserAccount must not be null");

        if (ratingId.isBlank())
            throw new IllegalArgumentException("[UpdateRatingCommand] ratingId cannot be blank");

        if (starRating < 1 || starRating > 5)
            throw new IllegalArgumentException("[UpdateRatingCommand] starRating must be between 1 and 5");

        if (comment != null && comment.length() > 250)
            throw new IllegalArgumentException("[UpdateRatingCommand] comment cannot exceed 250 characters");
    }
}
