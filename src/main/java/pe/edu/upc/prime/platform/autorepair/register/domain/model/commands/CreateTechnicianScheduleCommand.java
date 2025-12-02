package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import java.time.LocalDateTime;

/**
 * Command to create a new Technician Schedule.
 */
public record CreateTechnicianScheduleCommand(
        Technician technician,
        String dayOfWeek,
        String startTime,
        String endTime,
        Boolean isActive
) {
    public CreateTechnicianScheduleCommand {
        if (technician == null) {
            throw new IllegalArgumentException("[CreateTechnicianScheduleCommand] Technician cannot be null");
        }
        if (dayOfWeek == null || dayOfWeek.isBlank()) {
            throw new IllegalArgumentException("[CreateTechnicianScheduleCommand] Day of week cannot be null or blank");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("[CreateTechnicianScheduleCommand] Start and end time cannot be null");
        }
        if (isActive == null) {
            throw new IllegalArgumentException("[CreateTechnicianScheduleCommand] Active status cannot be null");
        }
    }
}
