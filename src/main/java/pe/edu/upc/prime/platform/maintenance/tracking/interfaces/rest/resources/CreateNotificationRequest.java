package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

/**
 * Request to create a notification.
 *
 * @param idNotification the identifier of the notification to be created
 * @param message the message content of the notification to be created
 * @param read indicates whether the notification has been read
 * @param idVehicle the identifier of the vehicle associated with the notification to be created
 * @param sent the date the notification was sent
 * @param idDiagnostic the identifier of the diagnostic associated with the notification to be created
 */
public record CreateNotificationRequest(
        @NotNull @NotBlank
        @JsonProperty("id_notification")
        String idNotification,

        @NotNull @NotBlank
        String message,

        boolean read,

        @NotNull @NotBlank
        @JsonProperty("id_vehicle")
        String idVehicle,

        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        LocalDate sent,

        @NotNull @NotBlank
        @JsonProperty("id_diagnostic")
        String idDiagnostic) {
}
