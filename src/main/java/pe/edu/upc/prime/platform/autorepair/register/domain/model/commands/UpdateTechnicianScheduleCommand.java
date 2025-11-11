package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to update an existing Technician Schedule.
 */
public record UpdateTechnicianScheduleCommand(
        String idTechnicianSchedule,
        String dayOfWeek,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isActive
) {
    public UpdateTechnicianScheduleCommand {
        if (idTechnicianSchedule == null || idTechnicianSchedule.isBlank()) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Id cannot be null or blank");
        }
        if (dayOfWeek == null || dayOfWeek.isBlank()) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Day of week cannot be null or blank");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Start and end time cannot be null");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] End time cannot be before start time");
        }
        if (isActive == null) {
            throw new IllegalArgumentException("[UpdateTechnicianScheduleCommand] Active status cannot be null");
        }
    }
}

