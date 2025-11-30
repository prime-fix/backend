package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories.NotificationRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the NotificationCommandService interface.
 */
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    /**
     * Repository for managing Notification entities.
     */
    private final NotificationRepository notificationRepository;

    /**
     * Constructor for NotificationCommandServiceImpl.
     *
     * @param notificationRepository The repository for managing Notification entities.
     */
    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Handles the creation of a new notification.
     *
     * @param command the command containing the notification information
     * @return the ID of the created notification
     */
    @Override
    public Long handle(CreateNotificationCommand command) {
        var notification = new Notification(command);
        try {
            this.notificationRepository.save(notification);
        } catch (Exception e) {
            throw new PersistenceException("[NotificationCommandServiceImpl] Error while saving notification: "
                    + e.getMessage());
        }
        return notification.getId();
    }

    /**
     * Handles the update of an existing notification.
     *
     * @param command the command containing the updated notification information
     * @return an Optional containing the updated notification, or empty if not found
     */
    @Override
    public Optional<Notification> handle(UpdateNotificationCommand command) {
        var notificationId = command.notificationId();

        if (!this.notificationRepository.existsById(notificationId)) {
            throw new NotFoundIdException(Notification.class, notificationId);
        }

        var notificationToUpdate = this.notificationRepository.findById(notificationId).get();
        notificationToUpdate.updateNotification(command);

        try {
            this.notificationRepository.save(notificationToUpdate);
            return Optional.of(notificationToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[NotificationCommandServiceImpl] Error while updating notification: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a notification.
     *
     * @param command the command containing the ID of the notification to be deleted
     */
    @Override
    public void handle(DeleteNotificationCommand command) {
        if (!this.notificationRepository.existsById(command.notificationId())) {
            throw new NotFoundIdException(Notification.class, command.notificationId());
        }

        try {
            this.notificationRepository.deleteById(command.notificationId());
        } catch (Exception e) {
            throw new PersistenceException("[NotificationCommandServiceImpl] Error while deleting notification: "
                    + e.getMessage());
        }
    }
}
