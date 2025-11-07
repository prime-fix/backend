package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

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
