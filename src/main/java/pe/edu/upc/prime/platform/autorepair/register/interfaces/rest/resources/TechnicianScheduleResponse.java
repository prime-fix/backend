package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDateTime;

/**
 * Resource representation of a Technician Schedule.
 *
 * @param id The unique identifier of the schedule.
 * @param technician_id         The identifier of the associated technician.
 * @param dayOfWeek            The day of the week of the schedule.
 * @param startTime            The start time of the schedule.
 * @param endTime              The end time of the schedule.
 * @param isActive             Indicates whether the schedule is active.
 */
public record TechnicianScheduleResponse(
        Long id,
        @JsonProperty("technician_id") Long technician_id,
        @JsonProperty("day_of_week") String dayOfWeek,
        @JsonProperty("start_time") String startTime,
        @JsonProperty("end_time") String endTime,
        @JsonProperty("is_active") Boolean isActive
) { }
