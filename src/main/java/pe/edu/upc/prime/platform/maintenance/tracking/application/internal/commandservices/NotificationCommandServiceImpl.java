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

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public String handle(CreateNotificationCommand command) {
        var idNotification = command.idNotification();

        if (notificationRepository.existsById(idNotification)) {
            throw new IllegalArgumentException("[NotificationCommandServiceImpl] Notification with ID "
                    + idNotification + " already exists");
        }

        var notification = new Notification(command);
        try {
            this.notificationRepository.save(notification);
        } catch (Exception e) {
            throw new PersistenceException("[NotificationCommandServiceImpl] Error while saving notification: "
                    + e.getMessage());
        }
        return notification.getIdNotification();
    }

    @Override
    public Optional<Notification> handle(UpdateNotificationCommand command) {
        var idNotification = command.idNotification();

        if (!this.notificationRepository.existsById(idNotification)) {
            throw new NotFoundIdException(Notification.class, idNotification);
        }

        var notificationToUpdate = this.notificationRepository.findById(idNotification).get();
        notificationToUpdate.updateNotification(command);

        try {
            this.notificationRepository.save(notificationToUpdate);
            return Optional.of(notificationToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[NotificationCommandServiceImpl] Error while updating notification: "
                    + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteNotificationCommand command) {
        if (!this.notificationRepository.existsById(command.idNotification())) {
            throw new NotFoundIdException(Notification.class, command.idNotification());
        }

        try {
            this.notificationRepository.deleteById(command.idNotification());
        } catch (Exception e) {
            throw new PersistenceException("[NotificationCommandServiceImpl] Error while deleting notification: "
                    + e.getMessage());
        }
    }
}
