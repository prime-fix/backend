package pe.edu.upc.prime.platform.data.collection.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * VisitCreatedEvent
 * Event that represents the creation of a Visit
 */
@Getter
public class VisitCreatedEvent extends ApplicationEvent {
    private final Long visitId;
    private final Long vehicleId;
    private final Long autoRepairId;
    private final Long serviceId;

    /**
     * VisitCreatedEvent Constructor
     * @param source The source of the event
     * @param visitId The visit id
     * @param vehicleId the vehicle id
     * @param autoRepairId the auto repair id
     * @param serviceId the service id
     */
    public VisitCreatedEvent(Object source, Long visitId, Long vehicleId, Long autoRepairId, Long serviceId) {
        super(source);
        this.visitId = visitId;
        this.vehicleId = vehicleId;
        this.autoRepairId = autoRepairId;
        this.serviceId = serviceId;
    }
}
