package pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdUserAccount;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreateRatingRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.RatingResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdateRatingRequest;

public class RatingAssembler {

    public static CreateRatingCommand toCommandFromRequest(CreateRatingRequest request) {
        return new CreateRatingCommand(
                request.starRating(),
                request.comment(),
                new IdAutoRepair(request.idAutoRepair()),
                new IdUserAccount(request.idUserAccount())
        );
    }

    public static UpdateRatingCommand toCommandFromRequest(UpdateRatingRequest request) {
        return new UpdateRatingCommand(
                request.id(),
                request.starRating(),
                request.comment(),
                null,
                null
        );
    }

    public static RatingResponse toResponseFromEntity(Rating entity) {
        return new RatingResponse(
                entity.getId(),
                entity.getStarRating(),
                entity.getComment(),

                entity.getIdAutoRepairValue(),
                entity.getIdUserAccountValue()
        );
    }
}