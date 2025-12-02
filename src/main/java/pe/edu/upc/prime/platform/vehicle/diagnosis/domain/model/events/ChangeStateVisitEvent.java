package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.StateVisit;

/**
 * Event triggered when the state of an expected visit is changed.
 */
@Getter
public class ChangeStateVisitEvent extends ApplicationEvent {
    /**
     * The ID of the vehicle for which the visit state is changed.
     */
    private final Long vehicleId;
    /**
     * The new state of the visit.
     */
    private final StateVisit stateVisit;

    /**
     * Gets the Expected Visit ID.
     *
     * @param source the source of the event
     * @param vehicleId  the ID of the vehicle
     * @param stateVisit the new state of the visit
     */
    public ChangeStateVisitEvent(Object source, Long vehicleId, StateVisit stateVisit) {
        super(source);
        this.vehicleId = vehicleId;
        this.stateVisit = stateVisit;
    }
}
