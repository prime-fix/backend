package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events.DiagnosticRegisteredEvent;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Event handler for DiagnosticRegisteredEvent.
 */
@Service
public class DiagnosticRegisteredEventHandler {
    /**
     * Service for handling notification commands.
     */
    private final NotificationCommandService notificationCommandService;
    /**
     * Logger for logging events and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticRegisteredEventHandler.class);

    /**
     * Constructor for DiagnosticRegisteredEventHandler.
     *
     * @param notificationCommandService the notification command service
     */
    public DiagnosticRegisteredEventHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    @EventListener
    public void on(DiagnosticRegisteredEvent event) {
        // Create a notification when a diagnostic is registered
        var createNotificationCommand = new CreateNotificationCommand(event.getDiagnosis(), event.getVehicleId(), LocalDate.now());
        LOGGER.info("Handling DiagnosticRegisteredEvent for Vehicle ID: {}", event.getVehicleId());

        // Invoke the command service to create the notification
        var notificationId = notificationCommandService.handle(createNotificationCommand);

        // Log the result of the notification creation
        if (Objects.isNull(notificationId)) {
            LOGGER.error("Failed to create notification for Vehicle ID: {}", event.getVehicleId());
        } else {
            LOGGER.info("Notification created with ID: {} for Vehicle ID: {}", notificationId, event.getVehicleId());
        }
    }
}
