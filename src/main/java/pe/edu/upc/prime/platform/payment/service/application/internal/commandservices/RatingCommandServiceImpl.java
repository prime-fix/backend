package pe.edu.upc.prime.platform.payment.service.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeleteRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingCommandService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.RatingRepository;

import java.util.Optional;

@Service
public class RatingCommandServiceImpl implements RatingCommandService {

    private final RatingRepository ratingRepository;

    public RatingCommandServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public String handle(CreateRatingCommand command) {
        var rating = new Rating(command);

        try {
            ratingRepository.save(rating);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[CreateRatingCommand] Error while saving rating: " + e.getMessage());
        }

        return rating.getId();
    }

    @Override
    public Optional<Rating> handle(UpdateRatingCommand command) {
        var ratingId = command.ratingId();

        var existingRating = ratingRepository.findById(ratingId);
        if (existingRating.isEmpty()) {
            throw new IllegalArgumentException(
                    "[UpdateRatingCommand] Rating with id " + ratingId + " does not exist");
        }

        var ratingToUpdate = existingRating.get();
        ratingToUpdate.updateRating(command);

        try {
            var updatedRating = ratingRepository.save(ratingToUpdate);
            return Optional.of(updatedRating);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[UpdateRatingCommand] Error while updating rating: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteRatingCommand command) {
        var ratingId = command.ratingId();

        if (!ratingRepository.existsById(ratingId)) {
            throw new IllegalArgumentException(
                    "[DeleteRatingCommand] Rating with id " + ratingId + " does not exist");
        }

        try {
            ratingRepository.deleteById(ratingId);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[DeleteRatingCommand] Error while deleting rating: " + e.getMessage());
        }
    }
}