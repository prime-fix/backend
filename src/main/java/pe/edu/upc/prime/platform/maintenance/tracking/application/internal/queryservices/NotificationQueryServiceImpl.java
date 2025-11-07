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

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return this.notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return Optional.ofNullable(this.notificationRepository.findById(query.idNotification())
        .orElseThrow(() -> new NotFoundIdException(Notification.class, query.idNotification())));
    }
}
