package pe.edu.upc.prime.platform.data.collection.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event representing the cancellation of an expected visit
 */
@Getter
public class CancelVisitEvent extends ApplicationEvent {

    /**
     * Identifier of the expected visit that was cancelled
     */
    private final Long visitId;

    /**
     * Creates a new instance of the CancelVisitEvent
     * @param source the component that published the event
     * @param visitId the unique identifier of the visit to cancel
     */
    public CancelVisitEvent(Object source, Long visitId) {
        super(source);
        this.visitId = visitId;
    }
}
