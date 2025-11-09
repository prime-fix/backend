package pe.edu.upc.prime.platform.payment.service.domain.services;

import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.*;

import java.util.Optional;


public interface RatingCommandService {
    /**
     * Handles the creation of a new Rating.
     * @param command The command containing rating details.
     * @return The ID of the created Rating.
     */
    String handle(CreateRatingCommand command);

    /**
     * Handles updating an existing Rating.
     * @param command The command containing updated data.
     * @return The updated Rating, if found.
     */
    Optional<Rating> handle(UpdateRatingCommand command);

    /**
     * Handles deleting an existing Rating.
     * @param command The command specifying which Rating to delete.
     */
    void handle(DeleteRatingCommand command);
}
