package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDateTime;

/**
 * Request to create a new Technician Schedule.
 *
 * @param technicianId         The identifier of the technician linked to this schedule.
 * @param dayOfWeek            The day of the week (e.g., MONDAY, TUESDAY).
 * @param startTime            The schedule start time.
 * @param endTime              The schedule end time.
 * @param isActive             Indicates whether the schedule is active.
 */
public record CreateTechnicianScheduleRequest(

        @NotNull @NotBlank
        @JsonProperty("technician_id")
        Long technicianId,

        @NotNull @NotBlank
        @JsonProperty("day_of_week")
        String dayOfWeek,

        @NotNull
        @JsonProperty("start_time")
        String startTime,

        @NotNull
        @JsonProperty("end_time")
        String endTime,

        @NotNull
        @JsonProperty("is_active")
        Boolean isActive
) { }
