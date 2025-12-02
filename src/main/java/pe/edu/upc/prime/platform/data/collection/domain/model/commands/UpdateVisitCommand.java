package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.ServiceId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Command to update an existing Visit
 *
 * @param failure the failure of the Visit
 * @param vehicleId the vehicleId of the Visit
 * @param timeVisit the time of the visit
 * @param autoRepairId the autoRepairId of the Visit
 * @param serviceId the Service ID of the visit
 */
public record UpdateVisitCommand(String failure, VehicleId vehicleId,
                                 LocalDateTime timeVisit, AutoRepairId autoRepairId, ServiceId serviceId) {

    public UpdateVisitCommand {
        Objects.requireNonNull(failure, "[UpdateVisitCommand] failure is required");
        Objects.requireNonNull(vehicleId, "[UpdateVisitCommand] vehicleId must not be null");
        Objects.requireNonNull(timeVisit, "[UpdateVisitCommand] timeVisit is required");
        Objects.requireNonNull(autoRepairId, "[UpdateVisitCommand] autoRepairId must not be null");
        Objects.requireNonNull(serviceId, "[UpdateVisitCommand] serviceId must not be null");

        if(timeVisit.isAfter(LocalDateTime.now().plusDays(30))){
            throw new IllegalArgumentException("[UpdateVisitCommand] timeVisit cannot be in the future");
        }
    }
}
