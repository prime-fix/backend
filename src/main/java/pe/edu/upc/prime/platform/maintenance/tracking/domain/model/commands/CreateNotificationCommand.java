package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to create a new notification.
 *
 * @param message the message content of the notification to be created
 * @param read indicates whether the notification has been read
 * @param idVehicle the identifier of the vehicle associated with the notification to be created
 * @param sent the date the notification was sent
 * @param idDiagnostic the identifier of the diagnostic associated with the notification to be created
 */
public record CreateNotificationCommand( String message, boolean read,
                                        String idVehicle, LocalDate sent, String idDiagnostic) {

    public CreateNotificationCommand {
        Objects.requireNonNull(message, "[CreateNotificationCommand] message must not be null");
        Objects.requireNonNull(idVehicle, "[CreateNotificationCommand] id vehicle must not be null");
        Objects.requireNonNull(sent, "[CreateNotificationCommand] sent date must not be null");
        Objects.requireNonNull(idDiagnostic, "[CreateNotificationCommand] id diagnostic must not be null");
    }
}
