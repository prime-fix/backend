package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDateTime;

/**
 * Request to create a new Technician Schedule.
 *
 * @param idTechnician         The identifier of the technician linked to this schedule.
 * @param dayOfWeek            The day of the week (e.g., MONDAY, TUESDAY).
 * @param startTime            The schedule start time.
 * @param endTime              The schedule end time.
 * @param isActive             Indicates whether the schedule is active.
 */
public record CreateTechnicianScheduleRequest(

        @NotNull @NotBlank
        @JsonProperty("id_technician")
        String idTechnician,

        @NotNull @NotBlank
        @JsonProperty("day_of_week")
        String dayOfWeek,

        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        @JsonProperty("start_time")
        LocalDateTime startTime,

        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        @JsonProperty("end_time")
        LocalDateTime endTime,

        @NotNull
        @JsonProperty("is_active")
        Boolean isActive
) { }
