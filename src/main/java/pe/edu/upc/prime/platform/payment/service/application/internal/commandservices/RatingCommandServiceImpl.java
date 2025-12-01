package pe.edu.upc.prime.platform.payment.service.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.application.internal.outboundservices.acl.ExternalAutoRepairCatalogServiceFromPaymentService;
import pe.edu.upc.prime.platform.payment.service.application.internal.outboundservices.acl.ExternalIamServiceFromPaymentService;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeleteRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingCommandService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.RatingRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

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
     * Service for interacting with external IAM services.
     */
    private final ExternalIamServiceFromPaymentService externalIamServiceFromPaymentService;

    /**
     * Service for interacting with external Auto Repair Catalog services.
     */
    private final ExternalAutoRepairCatalogServiceFromPaymentService externalAutoRepairCatalogServiceFromPaymentService;

    /**
     * Constructor for RatingCommandServiceImpl.
     *
     * @param ratingRepository the repository to access user data
     * @param externalIamServiceFromPaymentService service for interacting with external IAM services
     * @param externalAutoRepairCatalogServiceFromPaymentService service for interacting with external Auto Repair Catalog services
     */
    public RatingCommandServiceImpl(RatingRepository ratingRepository,
                                    ExternalIamServiceFromPaymentService externalIamServiceFromPaymentService,
                                    ExternalAutoRepairCatalogServiceFromPaymentService externalAutoRepairCatalogServiceFromPaymentService) {
        this.ratingRepository = ratingRepository;
        this.externalIamServiceFromPaymentService = externalIamServiceFromPaymentService;
        this.externalAutoRepairCatalogServiceFromPaymentService = externalAutoRepairCatalogServiceFromPaymentService;
    }

    /**
     * Handles the creation of a new rating based on the provided command.
     *
     * @param command the command containing the rating information
     * @return the ID of the newly created rating
     */
    @Override
    public Long handle(CreateRatingCommand command) {

        // Validate if user account ID exists in external IAM service
        if (!this.externalIamServiceFromPaymentService.existsUserAccountById(command.userAccountId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[RatingCommandServiceImpl] User Account ID: %s not found in the external IAM service",
                            command.userAccountId().value())
            );
        }

        // Validate if auto repair ID exists in external Auto Repair Catalog service
        if (!this.externalAutoRepairCatalogServiceFromPaymentService.existsAutoRepairById(command.autoRepairId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[RatingCommandServiceImpl] Auto Repair ID: %s not found in the external Auto Repair Catalog service",
                            command.autoRepairId().value())
            );
        }

        // Create a new Rating aggregate using the command data
        var rating = new Rating(command);

        try {
            ratingRepository.save(rating);
        } catch (Exception e) {
            throw new PersistenceException("[RatingCommandServiceImpl] Error creating rating: "
                    + e.getMessage());
        }
        return rating.getId();

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

        // Check if the rating exists
        if (!this.ratingRepository.existsById(ratingId)) {
            throw new NotFoundIdException(Rating.class, ratingId);
        }

        // Validate if user account ID exists in external IAM service
        if (!this.externalIamServiceFromPaymentService.existsUserAccountById(command.userAccountId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[RatingCommandServiceImpl] User Account ID: %s not found in the external IAM service.",
                            command.userAccountId().value())
            );
        }

        // Validate if auto repair ID exists in external Auto Repair Catalog service
        if (!this.externalAutoRepairCatalogServiceFromPaymentService.existsAutoRepairById(command.autoRepairId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[RatingCommandServiceImpl] Auto Repair ID: %s not found in the external Auto Repair Catalog service",
                            command.autoRepairId().value())
            );
        }

        // Retrieve the existing rating and update its details
        var ratingToUpdate = this.ratingRepository.findById(ratingId).get();
        ratingToUpdate.updateRating(command);

        try {
            this.ratingRepository.save(ratingToUpdate);
            return Optional.of(ratingToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[RatingCommandServiceImpl] Error while updating rating: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a rating based on the provided command.
     *
     * @param command the command containing the ID of the rating to be deleted
     */
    @Override
    public void handle(DeleteRatingCommand command) {
        // Check if the rating exists
        if (!this.ratingRepository.existsById(command.ratingId())) {
            throw new NotFoundIdException(Rating.class, command.ratingId());
        }

        // Delete the rating from the repository
        try {
            this.ratingRepository.deleteById(command.ratingId());
        }catch (Exception e) {
            throw new PersistenceException("[RatingCommandServiceImpl] Error deleting rating: "
                    + e.getMessage());
        }
    }
}