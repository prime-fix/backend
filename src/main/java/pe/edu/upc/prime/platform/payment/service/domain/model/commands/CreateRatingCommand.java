package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.Objects;

/**
 * Command to create a new Rating.
 *
 * @param starRating The number of stars (1–5).
 * @param comment The optional comment of the user.
 * @param autoRepairId The identifier of the auto repair being rated.
 * @param userAccountId The identifier of the user who made the rating.
 */

public record CreateRatingCommand(
        Integer starRating,
        String comment,
        AutoRepairId autoRepairId,
        UserAccountId userAccountId
) {
    public CreateRatingCommand {
        Objects.requireNonNull(starRating, "[CreateRatingCommand] starRating must not be null");
        Objects.requireNonNull(comment, "[CreateRatingCommand] comment must not be null");
        Objects.requireNonNull(autoRepairId, "[CreateRatingCommand] idAutoRepair must not be null");
        Objects.requireNonNull(userAccountId, "[CreateRatingCommand] idUserAccount must not be null");

        if (starRating < 1 || starRating > 5)
            throw new IllegalArgumentException("[CreateRatingCommand] starRating must be between 1 and 5");

        if (comment.length() > 250)
            throw new IllegalArgumentException("[CreateRatingCommand] comment cannot exceed 250 characters");
    }
}
