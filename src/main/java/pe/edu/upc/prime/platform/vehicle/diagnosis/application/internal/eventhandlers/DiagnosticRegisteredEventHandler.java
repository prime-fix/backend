package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.outboundservices.acl.ExternalMaintenanceTrackingServiceFromVehicleDiagnosis;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events.DiagnosticRegisteredEvent;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Event handler for DiagnosticRegisteredEvent.
 */
@Service
public class DiagnosticRegisteredEventHandler {
    /**
     * External service to interact with Maintenance Tracking
     */
    private final ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingService;
    /**
     * Logger for logging events and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticRegisteredEventHandler.class);

    /**
     * Constructor for DiagnosticRegisteredEventHandler.
     *
     * @param externalMaintenanceTrackingService the external maintenance tracking service from vehicle diagnosis
     */
    public DiagnosticRegisteredEventHandler(ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingService) {
        this.externalMaintenanceTrackingService = externalMaintenanceTrackingService;
    }

    @EventListener
    public void on(DiagnosticRegisteredEvent event) {
        // Create a notification when a diagnostic is registered
        LOGGER.info("Handling DiagnosticRegisteredEvent for Vehicle ID: {}", event.getVehicleId());
        var notificationId = externalMaintenanceTrackingService.createNotification(event.getDiagnosis(), event.getVehicleId(), LocalDate.now());

        // Log the result of the notification creation
        if (Objects.isNull(notificationId)) {
            LOGGER.error("Failed to create notification for Vehicle ID: {}", event.getVehicleId());
        } else {
            LOGGER.info("Notification created with ID: {} for Vehicle ID: {}", notificationId, event.getVehicleId());
        }
    }
}
