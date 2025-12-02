package pe.edu.upc.prime.platform.autorepair.register.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered when a Technician is deleted.
 */
@Getter
public class TechnicianDeletedEvent extends ApplicationEvent {
    /**
     * The Auto Repair ID associated with the deleted Technician.
     */
    private final Long autoRepairId;

    /**
     * Gets the Auto Repair ID.
     *
     * @param source the source of the event
     * @param autoRepairId the ID of the Auto Repair
     */
    public TechnicianDeletedEvent(Object source, Long autoRepairId) {
        super(source);
        this.autoRepairId = autoRepairId;
    }
}
