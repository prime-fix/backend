package pe.edu.upc.prime.platform.autorepair.register.domain.services;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianScheduleCommand;

import java.util.Optional;

public interface TechnicianScheduleCommandService {

    /**
     * Create a new TechnicianSchedule.
     *
     * @param command create command
     * @return id of created schedule
     */
    Long handle(CreateTechnicianScheduleCommand command);

    /**
     * Update an existing TechnicianSchedule.
     *
     * @param command update command
     * @return Optional with updated schedule
     */
    Optional<TechnicianSchedule> handle(UpdateTechnicianScheduleCommand command);

    /**
     * Delete an existing TechnicianSchedule.
     *
     * @param command delete command
     */
    void handle(DeleteTechnicianScheduleCommand command);
}
