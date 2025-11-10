package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Resource representation of a notification.
 *
 * @param idNotification the unique identifier of the notification
 * @param message the notification message
 * @param read indicates whether the notification has been read
 * @param idVehicle the identifier of the associated vehicle
 * @param sent the date the notification was sent
 * @param idDiagnostic the identifier of the associated diagnostic
 */
public record NotificationResponse(
        @JsonProperty("id_notification") String idNotification,
        String message,
        boolean read,
        @JsonProperty("id_vehicle") String idVehicle,
        LocalDate sent,
        @JsonProperty("id_diagnostic") String idDiagnostic) {
}
