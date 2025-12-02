package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Resource representation of a notification.
 *
 * @param id the unique identifier of the notification
 * @param message the notification message
 * @param read indicates whether the notification has been read
 * @param vehicleId the identifier of the associated vehicle
 * @param sent the date the notification was sent
 */
public record NotificationResponse(
        Long id,
        String message,
        Boolean read,
        @JsonProperty("vehicle_id") Long vehicleId,
        LocalDate sent) {
}
