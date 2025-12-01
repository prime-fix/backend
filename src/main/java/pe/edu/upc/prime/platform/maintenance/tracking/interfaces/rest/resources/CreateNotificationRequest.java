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
 * @param message the message content of the notification to be created
 * @param read indicates whether the notification has been read
 * @param vehicleId the identifier of the vehicle associated with the notification to be created
 * @param sent the date the notification was sent
 */
public record CreateNotificationRequest(
        @NotNull @NotBlank
        String message,

        @NotNull
        Boolean read,

        @NotNull @NotBlank
        @JsonProperty("vehicle_id")
        Long vehicleId,

        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        LocalDate sent) {
}
