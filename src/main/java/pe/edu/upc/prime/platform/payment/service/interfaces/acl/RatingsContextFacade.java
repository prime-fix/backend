package pe.edu.upc.prime.platform.payment.service.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeleteRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllRatingsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdAutoRepairQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.RatingAssembler;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.RatingResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Facade for managing Ratings — provides simplified access for other bounded contexts.
 */
@Service
public class RatingsContextFacade {

    private final RatingCommandService ratingCommandService;
    private final RatingQueryService ratingQueryService;

    public RatingsContextFacade(RatingCommandService ratingCommandService,
                                RatingQueryService ratingQueryService) {
        this.ratingCommandService = ratingCommandService;
        this.ratingQueryService = ratingQueryService;
    }

    /**
     * Retrieves all ratings.
     */
    public List<RatingResponse> fetchAllRatings() {
        var query = new GetAllRatingsQuery();
        return ratingQueryService.handle(query)
                .stream()
                .map(RatingAssembler::toResponseFromEntity)
                .toList();
    }

    /**
     * Retrieves ratings by auto repair.
     */
    public List<RatingResponse> fetchRatingsByAutoRepair(IdAutoRepair idAutoRepair) {
        var query = new GetRatingByIdAutoRepairQuery(idAutoRepair);
        return ratingQueryService.handle(query)
                .stream()
                .map(RatingAssembler::toResponseFromEntity)
                .toList();
    }

    /**
     * Fetches a rating by its ID.
     */
    public Optional<RatingResponse> fetchRatingById(String ratingId) {
        var query = new GetRatingByIdQuery(ratingId);
        var optionalRating = ratingQueryService.handle(query);
        return optionalRating.map(RatingAssembler::toResponseFromEntity);
    }

    /**
     * Creates a new rating.
     */
    public String createRating(int starRating, String comment,
                               String idAutoRepair, String idUserAccount) {
        var command = RatingAssembler.toCommandFromRequest(
                new pe.edu.upc.center.bc_pay_rate.payment.service.interfaces.rest.resources.CreateRatingRequest(
                        starRating, comment, idAutoRepair, idUserAccount));
        var ratingId = ratingCommandService.handle(command);
        return Objects.requireNonNullElse(ratingId, "0");
    }

    /**
     * Updates an existing rating.
     */
    public String updateRating(String id, int starRating, String comment) {
        var command = RatingAssembler.toCommandFromRequest(
                new pe.edu.upc.center.bc_pay_rate.payment.service.interfaces.rest.resources.UpdateRatingRequest(
                        id, starRating, comment));
        var optionalRating = ratingCommandService.handle(command);
        return optionalRating.map(Rating::getId).orElse("0");
    }

    /**
     * Deletes a rating by its ID.
     */
    public void deleteRating(String ratingId) {
        var command = new DeleteRatingCommand(ratingId);
        ratingCommandService.handle(command);
    }
}
