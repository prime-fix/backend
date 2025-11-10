package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

/**
 * Command to delete a notification.
 *
 * @param idNotification the unique identifier of the notification to be deleted
 */
public record DeleteNotificationCommand(String idNotification) {
}
