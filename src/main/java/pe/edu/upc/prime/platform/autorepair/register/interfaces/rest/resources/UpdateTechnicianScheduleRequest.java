package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDateTime;

/**
 * Request to update an existing Technician Schedule.
 *
 * @param dayOfWeek  The updated day of the week.
 * @param startTime  The updated start time.
 * @param endTime    The updated end time.
 * @param isActive   Indicates whether the schedule remains active.
 */
public record UpdateTechnicianScheduleRequest(
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
