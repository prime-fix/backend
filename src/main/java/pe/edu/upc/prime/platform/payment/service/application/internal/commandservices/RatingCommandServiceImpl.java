package pe.edu.upc.prime.platform.payment.service.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeleteRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingCommandService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.RatingRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;

import java.util.Optional;

/**
 * Implementation of the RatingCommandService interface.
 */
@Service
public class RatingCommandServiceImpl implements RatingCommandService {

    /**
     * The repository to access user data.
     */
    private final RatingRepository ratingRepository;

    /**
     * Constructor for RatingCommandServiceImpl.
     *
     * @param ratingRepository the repository to access user data
     */
    public RatingCommandServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Handles the creation of a new rating based on the provided command.
     *
     * @param command the command containing the rating information
     * @return the ID of the newly created rating
     */
    @Override
    public String handle(CreateRatingCommand command) {
        var rating = new Rating(command);

        try {
            ratingRepository.save(rating);
            return rating.getIdRating();
        } catch (Exception e) {
            throw new PersistenceException(
                    "Error creating rating: " + e.getMessage());
        }

    }

    /**
     * Handles the update of an existing rating based on the provided command.
     *
     * @param command the command containing the updated rating information
     * @return an Optional containing the updated Rating if successful
     */
    @Override
    public Optional<Rating> handle(UpdateRatingCommand command) {
        var ratingId = command.ratingId();

        if (!this.ratingRepository.existsById(ratingId)) {
            throw new NotFoundArgumentException(
                    String.format("Rating with the same id %s does not exist.", ratingId)
            );
        }

        var ratingToUpdate = this.ratingRepository.findById(ratingId).get();
        ratingToUpdate.updateRating(command);

        try {
            this.ratingRepository.save(ratingToUpdate);
            return Optional.of(ratingToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("Error updating rating: " + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a rating based on the provided command.
     *
     * @param command the command containing the ID of the rating to be deleted
     */
    @Override
    public void handle(DeleteRatingCommand command) {
        if (!this.ratingRepository.existsById(command.ratingId())) {
            throw new NotFoundArgumentException(
                    String.format("Rating with the same id %s does not exist.", command.ratingId())
            );
        }

        this.ratingRepository.findById(command.ratingId()).ifPresent(optionalRating -> {
            this.ratingRepository.deleteById(optionalRating.getIdRating());
        });
    }
}