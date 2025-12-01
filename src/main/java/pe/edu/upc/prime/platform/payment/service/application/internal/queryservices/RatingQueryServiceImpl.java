package pe.edu.upc.prime.platform.payment.service.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllRatingsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdAutoRepairQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingQueryService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.RatingRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the RatingQueryService interface.
 */
@Service
public class RatingQueryServiceImpl implements RatingQueryService {

    /**
     * The repository to access user account data.
     */
    private final RatingRepository ratingRepository;

    /**
     * Constructor for RatingQueryServiceImpl.
     *
     * @param ratingRepository the repository to access rating data
     */
    public RatingQueryServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Get all ratings.
     *
     * @param query the query to get all ratings
     * @return a list of all ratings
     */
    @Override
    public List<Rating> handle(GetAllRatingsQuery query) {
        return ratingRepository.findAll();
    }

    /**
     * Get a rating by its ID.
     *
     * @param query the query containing the rating ID
     * @return an optional containing the rating if found
     */
    @Override
    public Optional<Rating> handle(GetRatingByIdQuery query) {
        return Optional.ofNullable(this.ratingRepository.findById(query.ratingId())
                .orElseThrow(() -> new NotFoundIdException(Rating.class,query.ratingId())));
    }

    /**
     * Get a rating by its auto repair ID.
     *
     * @param query the query containing the auto repair ID
     * @return an optional containing the rating if found
     */
    @Override
    public List<Rating> handle(GetRatingByIdAutoRepairQuery query) {
        return ratingRepository.findByAutoRepairId(query.idAutoRepair());
    }
}
