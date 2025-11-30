package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

import java.util.Objects;

public record GetRatingByIdAutoRepairQuery(AutoRepairId idAutoRepair){

    public GetRatingByIdAutoRepairQuery {
        Objects.requireNonNull(idAutoRepair, "[GetRatingByIdAutoRepair] idAutoRepair cannot be null");
    }
}
