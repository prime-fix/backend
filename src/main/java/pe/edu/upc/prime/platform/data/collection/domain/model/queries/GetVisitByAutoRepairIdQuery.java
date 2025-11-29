package pe.edu.upc.prime.platform.data.collection.domain.model.queries;

import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;

import java.util.Objects;

/**
 * Query to get a visit by autoRepair ID
 * @param autoRepairId the autoRepairID
 */
public record GetVisitByAutoRepairIdQuery(Long autoRepairId) {

    public GetVisitByAutoRepairIdQuery {
        Objects.requireNonNull(autoRepairId);
    }
}
