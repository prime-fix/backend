package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered when a new diagnostic is registered.
 */
@Getter
public class DiagnosticRegisteredEvent extends ApplicationEvent {
    /**
     * The ID of the vehicle for which the diagnostic is registered.
     */
    private final Long vehicleId;
    /**
     * The diagnosis details.
     */
    private final String diagnosis;

    /**
     * Gets the Diagnostic ID.
     *
     * @param source the source of the event
     * @param vehicleId  the ID of the vehicle
     * @param diagnosis the diagnosis details
     */
    public DiagnosticRegisteredEvent(Object source, Long vehicleId, String diagnosis) {
        super(source);
        this.vehicleId = vehicleId;
        this.diagnosis = diagnosis;
    }
}
