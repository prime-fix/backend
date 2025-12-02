package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.events.ChangeMaintenanceStatusEvent;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Event handler for ChangeMaintenanceStatusEvent.
 */
@Service
public class ChangeMaintenanceStatusEventHandler {
    /**
     * Service for handling notification commands.
     */
    private final NotificationCommandService notificationCommandService;
    /**
     * Logger for logging events and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeMaintenanceStatusEventHandler.class);

    /**
     * Constructor for ChangeMaintenanceStatusEventHandler.
     *
     * @param notificationCommandService the notification command service
     */
    public ChangeMaintenanceStatusEventHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Handles the ChangeMaintenanceStatusEvent.
     *
     * @param event the change maintenance status event
     */
    @EventListener
    public void on(ChangeMaintenanceStatusEvent event) {
        // Create a notification when maintenance status changes
        var createNotificationCommand = new CreateNotificationCommand(event.getMaintenanceStatus().getNotificationMessage(),
                event.getVehicleId(), LocalDate.now());
        LOGGER.info("Handling ChangeMaintenanceStatusEvent for Vehicle ID: {}", event.getVehicleId());

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
