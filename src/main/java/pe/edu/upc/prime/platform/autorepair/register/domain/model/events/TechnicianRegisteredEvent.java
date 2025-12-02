package pe.edu.upc.prime.platform.autorepair.register.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered when a Technician is registered.
 */
@Getter
public class TechnicianRegisteredEvent extends ApplicationEvent {
    /**
     * The Auto Repair ID associated with the registered Technician.
     */
    private final Long autoRepairId;

    /**
     * Gets the Auto Repair ID.
     *
     * @param source the source of the event
     * @param autoRepairId the ID of the Auto Repair
     */
    public TechnicianRegisteredEvent(Object source, Long autoRepairId) {
        super(source);
        this.autoRepairId = autoRepairId;
    }
}
