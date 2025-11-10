package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetAllNotificationsQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetNotificationByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories.NotificationRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the NotificationQueryService interface.
 */
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {
    /**
     * The Notification repository.
     */
    private final NotificationRepository notificationRepository;

    /**
     * Constructor for NotificationQueryServiceImpl.
     *
     * @param notificationRepository The Notification repository.
     */
    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Handles the GetAllNotificationsQuery to retrieve all notifications.
     *
     * @param query the query to get all notifications
     * @return a list of all notifications
     */
    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return this.notificationRepository.findAll();
    }

    /**
     * Handles the GetNotificationByIdQuery to retrieve a notification by its ID.
     *
     * @param query the query containing the notification ID
     * @return an Optional containing the notification if found, or empty if not found
     */
    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return Optional.ofNullable(this.notificationRepository.findById(query.idNotification())
        .orElseThrow(() -> new NotFoundIdException(Notification.class, query.idNotification())));
    }
}
