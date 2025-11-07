package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.CreateNotificationRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.NotificationResponse;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.UpdateNotificationRequest;

public class NotificationAssembler {

    public static CreateNotificationCommand toCommandFromRequest(CreateNotificationRequest request) {
        return new CreateNotificationCommand(
                request.idNotification(), request.message(), request.read(),
                request.idVehicle(), request.sent(), request.idDiagnostic()
        );
    }


    public static UpdateNotificationCommand toCommandFromRequest(String idNotification, UpdateNotificationRequest request) {
        return new UpdateNotificationCommand(
                idNotification, request.message(), request.read(),
                request.idVehicle(), request.sent(), request.idDiagnostic()
        );
    }

    public static NotificationResponse toResponseFromEntity(Notification entity) {
        return new NotificationResponse(entity.getIdNotification(), entity.getMessage(),
                entity.isRead(), entity.getIdVehicle(), entity.getSent(),
                entity.getIdDiagnostic());
    }
}
