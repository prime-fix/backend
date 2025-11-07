package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

public record NotificationResponse(
        @JsonProperty("id_notification") String idNotification,
        String message,
        boolean read,
        @JsonProperty("id_vehicle") String idVehicle,
        LocalDate sent,
        @JsonProperty("id_diagnostic") String idDiagnostic) {
}
