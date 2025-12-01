package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries;

/**
 * Query to check if a notification exists by its id.
 *
 * @param notificationId the id of the notification.
 */
public record ExistsNotificationByIdQuery(Long notificationId) {
}
