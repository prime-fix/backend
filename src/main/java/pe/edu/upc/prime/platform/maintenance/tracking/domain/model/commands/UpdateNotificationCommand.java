package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to update an existing notification.
 *
 * @param idNotification the unique identifier of the notification to be updated
 * @param message the updated message content of the notification
 * @param read the updated read status of the notification
 * @param idVehicle the identifier of the vehicle associated with the notification
 * @param sent the updated date the notification was sent
 * @param idDiagnostic the identifier of the diagnostic associated with the notification
 */
public record UpdateNotificationCommand(String idNotification, String message, boolean read,
                                        String idVehicle, LocalDate sent, String idDiagnostic) {
    public UpdateNotificationCommand {
        Objects.requireNonNull(idNotification, "[UpdateNotificationCommand] id notification must not be null");
        Objects.requireNonNull(message, "[UpdateNotificationCommand] message must not be null");
        Objects.requireNonNull(idVehicle, "[UpdateNotificationCommand] id vehicle must not be null");
        Objects.requireNonNull(sent, "[UpdateNotificationCommand] sent date must not be null");
        Objects.requireNonNull(idDiagnostic, "[UpdateNotificationCommand] id diagnostic must not be null");
    }
}
