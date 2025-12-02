package pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreateRatingRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.RatingResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdateRatingRequest;

/**
 * Assembler class for converting between Rating-related requests, commands, and responses.
 */
public class RatingAssembler {
    /**
     * Convert CreateRatingRequest to CreateRatingCommand.
     *
     * @param request The create rating request.
     * @return The corresponding to create rating command.
     */
    public static CreateRatingCommand toCommandFromRequest(CreateRatingRequest request) {
        return new CreateRatingCommand(
                request.starRating(),
                request.comment(),
                new AutoRepairId(request.autoRepairId()),
                new UserAccountId(request.userAccountId())
        );
    }

    /**
     * Convert UpdateRatingRequest to UpdateRatingCommand.
     *
     * @param ratingId The ID of the rating to update.
     * @param request The update rating request.
     * @return The corresponding to update rating command.
     */
    public static UpdateRatingCommand toCommandFromRequest(Long ratingId, UpdateRatingRequest request) {
        return new UpdateRatingCommand(
                ratingId,
                request.starRating(),
                request.comment(),
                new AutoRepairId(request.autoRepairId()),
                new UserAccountId(request.userAccountId())
        );
    }

    /**
     * Convert Rating entity to RatingResponse.
     *
     * @param entity The rating entity.
     * @return The corresponding rating response.
     */
    public static RatingResponse toResponseFromEntity(Rating entity) {
        return new RatingResponse(
                entity.getId(),
                entity.getStarRating(),
                entity.getComment(),
                entity.getAutoRepairId().value(),
                entity.getUserAccountId().value()
        );
    }
}