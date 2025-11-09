package pe.edu.upc.prime.platform.payment.service.domain.services;

import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface RatingQueryService {
    /**
     * Retrieves all ratings.
     */
    List<Rating> handle(GetAllRatingsQuery query);

    /**
     * Retrieves a rating by its unique identifier.
     */
    Optional<Rating> handle(GetRatingByIdQuery query);

    /**
     * Retrieves all ratings for a specific auto repair shop.
     */
    List<Rating> handle(GetRatingByIdAutoRepairQuery query);
}
