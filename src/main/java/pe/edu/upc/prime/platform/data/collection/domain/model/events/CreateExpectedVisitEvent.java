package pe.edu.upc.prime.platform.data.collection.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * CreateExpectedVisitEvent
 * Event representing the creation of an expected visit
 */
@Getter
public class CreateExpectedVisitEvent extends ApplicationEvent {

    /**
     * Identifier of the expected visit that was created.
     */
    private final Long visitId;

    /**
     * Identifier of the vehicle associated with the expected visit.
     */
    private final Long vehicleId;

    /**
     * Creates a new instance of the CreateExpectedVisitEvent.
     *
     * @param source   the component that published the event
     * @param visitId  the unique identifier of the expected visit
     * @param vehicleId the unique identifier of the vehicle involved in the visit
     */
    public CreateExpectedVisitEvent(Object source ,Long visitId, Long vehicleId
    ) {
        super(source);
        this.visitId = visitId;
        this.vehicleId = vehicleId;
    }
}