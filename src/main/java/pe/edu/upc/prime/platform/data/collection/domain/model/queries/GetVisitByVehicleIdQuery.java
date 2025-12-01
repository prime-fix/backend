package pe.edu.upc.prime.platform.data.collection.domain.model.queries;

import java.util.Objects;

/**
 * Query to get a visit by a vehicleID
 * @param vehicleId the vehicleID
 */
public record GetVisitByVehicleIdQuery(Long vehicleId) {

    public GetVisitByVehicleIdQuery {
        Objects.requireNonNull(vehicleId);
    }
}
