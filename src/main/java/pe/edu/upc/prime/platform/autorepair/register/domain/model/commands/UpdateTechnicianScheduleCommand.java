package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Command to update a technician's schedule.
 *
 * @param technicianScheduleId the ID of the schedule to update
 * @param dayOfWeek the day of the week for the schedule
 * @param startTime the start time of the schedule
 * @param endTime the end time of the schedule
 * @param isActive the active status of the schedule
 */
public record UpdateTechnicianScheduleCommand(
        Long technicianScheduleId,
        String dayOfWeek,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isActive
) {
    public UpdateTechnicianScheduleCommand {
        if (Objects.isNull(technicianScheduleId) || technicianScheduleId <= 0) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Id cannot be null or negative");
        }
        if (Objects.isNull(dayOfWeek) || dayOfWeek.isBlank()) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Day of week cannot be null or blank");
        }
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Start and end time cannot be null");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] End time cannot be before start time");
        }
        if (Objects.isNull(isActive)) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Active status cannot be null");
        }
    }
}

