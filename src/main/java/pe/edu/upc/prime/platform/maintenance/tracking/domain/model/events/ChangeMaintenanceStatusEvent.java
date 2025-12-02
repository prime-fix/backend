package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.MaintenanceStatus;

/**
 * Event triggered when the maintenance status of a notification is changed.
 */
@Getter
public class ChangeMaintenanceStatusEvent extends ApplicationEvent {
    /**
     * The ID of the vehicle whose maintenance status has changed.
     */
    private final Long vehicleId;

    /**
     * The new maintenance status of the vehicle.
     */
    private final MaintenanceStatus maintenanceStatus;

    /**
     * Gets the Vehicle ID.
     *
     * @param source the source of the event
     * @param vehicleId the ID of the vehicle
     */
    public ChangeMaintenanceStatusEvent(Object source, Long vehicleId, MaintenanceStatus maintenanceStatus) {
        super(source);
        this.vehicleId = vehicleId;
        this.maintenanceStatus = maintenanceStatus;
    }
}
