package pe.edu.upc.prime.platform.maintenance.tracking.domain.services;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateNotificationCommand;

import java.util.Optional;

/**
 * Service interface for handling notification-related commands.
 */
public interface NotificationCommandService {
    /**
     * Handles the creation of a new notification based on the provided command.
     *
     * @param command the command containing the notification information
     * @return the ID of the newly created notification
     */
    Long handle(CreateNotificationCommand command);

    /**
     * Handles the update of a notification based on the provided command.
     *
     * @param command the command containing the updated notification information
     * @return the updated Notification wrapped in an Optional, or empty if not found
     */
    Optional<Notification> handle(UpdateNotificationCommand command);

    /**
     * Handles the deletion of a notification based on the provided command.
     *
     * @param command the command containing the ID of the notification to be deleted
     */
    void handle(DeleteNotificationCommand command);
}
