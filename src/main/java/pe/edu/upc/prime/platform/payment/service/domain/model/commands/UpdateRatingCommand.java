package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.Objects;

/**
 * Command to update an existing Rating.
 *
 * @param ratingId The ID of the rating to update.
 * @param starRating The new star rating (1–5).
 * @param comment The new comment.
 * @param autoRepairId The auto repair associated with this rating.
 * @param userAccountId The user account that made the rating.
 */

public record UpdateRatingCommand(
        Long ratingId,
        Integer starRating,
        String comment,
        AutoRepairId autoRepairId,
        UserAccountId userAccountId
) {
    public UpdateRatingCommand {
        Objects.requireNonNull(ratingId, "[UpdateRatingCommand] ratingId must not be null");
        Objects.requireNonNull(starRating, "[CreateRatingCommand] starRating must not be null");
        Objects.requireNonNull(comment, "[CreateRatingCommand] comment must not be null");
        Objects.requireNonNull(autoRepairId, "[UpdateRatingCommand] idAutoRepair must not be null");
        Objects.requireNonNull(userAccountId, "[UpdateRatingCommand] idUserAccount must not be null");

        if (ratingId <= 0)
            throw new IllegalArgumentException("[UpdateRatingCommand] ratingId cannot be less than or equal to 0");

        if (starRating < 1 || starRating > 5)
            throw new IllegalArgumentException("[UpdateRatingCommand] starRating must be between 1 and 5");

        if (comment.length() > 250)
            throw new IllegalArgumentException("[UpdateRatingCommand] comment cannot exceed 250 characters");
    }
}
