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

@Service
public class RatingQueryServiceImpl implements RatingQueryService {

    private final RatingRepository ratingRepository;

    public RatingQueryServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> handle(GetAllRatingsQuery query) {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> handle(GetRatingByIdQuery query) {
        return Optional.ofNullable(this.ratingRepository.findById(query.ratingId())
                .orElseThrow(() -> new NotFoundIdException(Rating.class,query.ratingId())));
    }

    @Override
    public List<Rating> handle(GetRatingByIdAutoRepairQuery query) {
        return ratingRepository.findByIdAutoRepair(query.idAutoRepair());
    }
}
