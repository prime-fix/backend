package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDateTime;

/**
 * Resource representation of a Technician Schedule.
 *
 * @param idTechnicianSchedule The unique identifier of the schedule.
 * @param idTechnician         The identifier of the associated technician.
 * @param dayOfWeek            The day of the week of the schedule.
 * @param startTime            The start time of the schedule.
 * @param endTime              The end time of the schedule.
 * @param isActive             Indicates whether the schedule is active.
 */
public record TechnicianScheduleResponse(
        @JsonProperty("id_technician_schedule") String idTechnicianSchedule,
        @JsonProperty("id_technician") String idTechnician,
        @JsonProperty("day_of_week") String dayOfWeek,
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        @JsonProperty("start_time") LocalDateTime startTime,
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        @JsonProperty("end_time") LocalDateTime endTime,
        @JsonProperty("is_active") Boolean isActive
) { }
