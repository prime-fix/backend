package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.CreateNotificationRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.NotificationResponse;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.UpdateNotificationRequest;

import java.time.LocalDate;

/**
 * Assembler for converting between Notification-related requests, commands, and responses.
 */
public class NotificationAssembler {
    /**
     * Converts a CreateNotificationRequest to a CreateNotificationCommand.
     *
     * @param request The create notification request.
     * @return The corresponding to create notification command.
     */
    public static CreateNotificationCommand toCommandFromRequest(CreateNotificationRequest request) {
        return new CreateNotificationCommand(
                 request.message(), request.read(),
                request.vehicleId(), request.sent()
        );
    }

    /**
     * Converts an UpdateNotificationRequest to an UpdateNotificationCommand.
     *
     * @param notificationId The ID of the notification to update.
     * @param request The update notification request.
     * @return The corresponding update notification command.
     */
    public static UpdateNotificationCommand toCommandFromRequest(Long notificationId, UpdateNotificationRequest request) {
        return new UpdateNotificationCommand(
                notificationId, request.message(), request.read(),
                request.vehicleId(), request.sent()
        );
    }

    /**
     * Converts a Notification entity to a NotificationResponse.
     *
     * @param entity The notification entity.
     * @return The corresponding notification response.
     */
    public static NotificationResponse toResponseFromEntity(Notification entity) {
        return new NotificationResponse(entity.getId(), entity.getMessage(),
                entity.getRead(), entity.getVehicle().getId(), entity.getSent());
    }

    /**
     * Converts raw values to a CreateNotificationCommand.
     *
     * @param message the notification message
     * @param read the read status
     * @param vehicleId the associated vehicle ID
     * @param sent the date the notification was sent
     * @return the corresponding CreateNotificationCommand
     */
    public static CreateNotificationCommand toCommandFromValues(String message, Boolean read,
                                                                Long vehicleId, LocalDate sent) {
        return new CreateNotificationCommand(message, read, vehicleId, sent);
    }

    /**
     * Converts raw values to an UpdateNotificationCommand.
     *
     * @param notificationId the ID of the notification to update
     * @param message the notification message
     * @param read the read status
     * @param vehicleId the associated vehicle ID
     * @param sent the date the notification was sent
     * @return the corresponding UpdateNotificationCommand
     */
    public static UpdateNotificationCommand toCommandFromValues(Long notificationId, String message,
                                                                Boolean read, Long vehicleId, LocalDate sent) {
        return new UpdateNotificationCommand(notificationId, message, read, vehicleId, sent);
    }

}
