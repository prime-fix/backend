package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.CreateNotificationRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.NotificationResponse;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.UpdateNotificationRequest;

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
                request.idVehicle(), request.sent(), request.idDiagnostic()
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
                request.idVehicle(), request.sent(), request.idDiagnostic()
        );
    }

    /**
     * Converts a Notification entity to a NotificationResponse.
     *
     * @param entity The notification entity.
     * @return The corresponding notification response.
     */
    public static NotificationResponse toResponseFromEntity(Notification entity) {
        return new NotificationResponse(entity.getId().toString(), entity.getMessage(),
                entity.isRead(), entity.getIdVehicle(), entity.getSent(),
                entity.getIdDiagnostic());
    }
}
