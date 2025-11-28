package pe.edu.upc.prime.platform.payment.service.domain.model.commands;

import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;

import java.util.Objects;

/**
 * Command to create a new Rating.
 *
 * @param starRating The number of stars (1–5).
 * @param comment The optional comment of the user.
 * @param idAutoRepair The identifier of the auto repair being rated.
 * @param idUserAccount The identifier of the user who made the rating.
 */

public record CreateRatingCommand(

        int starRating,
        String comment,
        IdAutoRepair idAutoRepair,
        IdUserAccount idUserAccount
) {
    public CreateRatingCommand {
        Objects.requireNonNull(idAutoRepair, "[CreateRatingCommand] idAutoRepair must not be null");
        Objects.requireNonNull(idUserAccount, "[CreateRatingCommand] idUserAccount must not be null");

        if (starRating < 1 || starRating > 5)
            throw new IllegalArgumentException("[CreateRatingCommand] starRating must be between 1 and 5");

        if (comment != null && comment.length() > 250)
            throw new IllegalArgumentException("[CreateRatingCommand] comment cannot exceed 250 characters");
    }
}
