package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.StateVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VisitId;

import java.util.Objects;

/**
 * Command to update an Expected Visit
 *
 * @param expectedVisitId the identifier of the expected visit
 * @param stateVisit the state of the visit
 * @param visitId the identifier of the visit
 * @param isScheduled indicates if the visit is scheduled
 */
public record UpdateExpectedVisitCommand(Long expectedVisitId, StateVisit stateVisit, VisitId visitId, Boolean isScheduled) {
    public UpdateExpectedVisitCommand {
        Objects.requireNonNull(expectedVisitId, "[UpdateExpectedVisitCommand] expected visit id must not be null");
        Objects.requireNonNull(stateVisit, "[UpdateExpectedVisitCommand] state visit must not be null");
        Objects.requireNonNull(visitId, "[UpdateExpectedVisitCommand] visit id must not be null");
        Objects.requireNonNull(isScheduled, "[UpdateExpectedVisitCommand] the flag about is scheduled the visit must not be null");

        if (stateVisit != StateVisit.PENDING_VISIT &&
        stateVisit != StateVisit.SCHEDULED_VISIT &&
        stateVisit != StateVisit.CANCELLED_VISIT) {
            throw new IllegalArgumentException("[UpdateExpectedVisitCommand] Invalid state visit [" + stateVisit.name() + "]");
        }
    }
}
