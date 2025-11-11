package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


/**
 * Command to create a new Visit
 * @param visitId The ID of the visit
 * @param failure The failure of the Visit
 * @param vehicleId The vehicleId of the Visit
 * @param timeVisit The time of the visit
 * @param autoRepairId The autoRepairId of the Visit
 * @param serviceId the Service ID of the visit
 */
public record CreateVisitCommand(String visitId, String failure, String vehicleId,
Date timeVisit, String autoRepairId, String serviceId) {

    public CreateVisitCommand {
        Objects.requireNonNull(visitId, "[CreateVisitCommand] visitId must not be null");
        Objects.requireNonNull(failure, "[CreateVisitCommand] failure is required");
        Objects.requireNonNull(vehicleId, "[CreateVisitCommand] vehicleId must not be null");
        Objects.requireNonNull(timeVisit, "[CreateVisitCommand] timeVisit is required");
        Objects.requireNonNull(autoRepairId, "[CreateVisitCommand] autoRepairId must not be null");
        Objects.requireNonNull(serviceId, "[CreateVisitCommand] serviceId must not be null");

        if(timeVisit.after(new Date())){
            throw new IllegalArgumentException("[CreateVisitCommand] timeVisit cannot be in the future");
        }
    }

}
