package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

/**
 * Request to update a notification.
 *
 * @param message the notification message to be updated
 * @param read indicates whether the notification has been read
 * @param idVehicle the identifier of the associated vehicle
 * @param sent the date the notification was sent
 * @param idDiagnostic the identifier of the associated diagnostic
 */
public record UpdateNotificationRequest(
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
        String idDiagnostic
) {
}
