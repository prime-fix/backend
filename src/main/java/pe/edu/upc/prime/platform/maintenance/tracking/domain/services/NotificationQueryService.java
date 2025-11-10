package pe.edu.upc.prime.platform.maintenance.tracking.domain.services;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetAllNotificationsQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetNotificationByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling notification-related queries.
 */
public interface NotificationQueryService {
    /**
     * Handle the query to get all notifications.
     *
     * @param query the query to get all notifications
     * @return a list of all notifications
     */
    List<Notification> handle(GetAllNotificationsQuery query);

    /**
     * Handle the query to get a notification by its ID.
     *
     * @param query the query containing the notification ID
     * @return an optional notification matching the ID
     */
    Optional<Notification> handle(GetNotificationByIdQuery query);
}
