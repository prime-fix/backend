package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

import java.util.Objects;

/**
 * Query to get ratings by auto repair ID.
 *
 * @param idAutoRepair the ID of the auto repair to retrieve ratings for
 */
public record GetRatingByIdAutoRepairQuery(AutoRepairId idAutoRepair){

    public GetRatingByIdAutoRepairQuery {
        Objects.requireNonNull(idAutoRepair, "[GetRatingByIdAutoRepair] idAutoRepair cannot be null");
    }
}
