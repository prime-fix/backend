package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to create a new notification.
 *
 * @param message the message content of the notification to be created
 * @param vehicleId the identifier of the vehicle associated with the notification to be created
 * @param sent the date the notification was sent
 */
public record CreateNotificationCommand(String message, Long vehicleId, LocalDate sent) {

    public CreateNotificationCommand {
        Objects.requireNonNull(message, "[CreateNotificationCommand] message must not be null");
        Objects.requireNonNull(vehicleId, "[CreateNotificationCommand] vehicle id must not be null");
        Objects.requireNonNull(sent, "[CreateNotificationCommand] sent date must not be null");
    }
}
