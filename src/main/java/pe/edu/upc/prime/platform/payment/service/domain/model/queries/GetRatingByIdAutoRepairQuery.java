package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdAutoRepair;

import java.util.Objects;

public record GetRatingByIdAutoRepairQuery(IdAutoRepair idAutoRepair){

    public GetRatingByIdAutoRepairQuery {
        Objects.requireNonNull(idAutoRepair, "[GetRatingByIdAutoRepair] idAutoRepair cannot be null");
    }
}
