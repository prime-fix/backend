package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.ServiceId;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.VehicleId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;


/**
 * Command to create a new Visit
 * @param failure The failure of the Visit
 * @param vehicleId The vehicleId of the Visit
 * @param timeVisit The time of the visit
 * @param autoRepairId The autoRepairId of the Visit
 * @param serviceId the Service ID of the visit
 */
public record CreateVisitCommand(String failure, VehicleId vehicleId,
                                 LocalDateTime timeVisit, AutoRepairId autoRepairId, ServiceId serviceId) {

    public CreateVisitCommand {
        Objects.requireNonNull(failure, "[CreateVisitCommand] failure is required");
        Objects.requireNonNull(vehicleId, "[CreateVisitCommand] vehicleId must not be null");
        Objects.requireNonNull(timeVisit, "[CreateVisitCommand] timeVisit is required");
        Objects.requireNonNull(autoRepairId, "[CreateVisitCommand] autoRepairId must not be null");
        Objects.requireNonNull(serviceId, "[CreateVisitCommand] serviceId must not be null");

        if(timeVisit.isAfter(LocalDateTime.now().plusDays(30))){
            throw new IllegalArgumentException("[CreateVisitCommand] timeVisit cannot be in the future");
        }
    }

}
