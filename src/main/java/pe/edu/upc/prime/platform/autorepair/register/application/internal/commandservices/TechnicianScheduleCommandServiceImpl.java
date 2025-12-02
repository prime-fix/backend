package pe.edu.upc.prime.platform.autorepair.register.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianScheduleCommandService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianRepository;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianScheduleRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the TechnicianScheduleCommandService interface.
 * Handles commands related to creating, updating, and deleting Technician Schedules.
 */
@Service
public class TechnicianScheduleCommandServiceImpl implements TechnicianScheduleCommandService {

    private final TechnicianScheduleRepository technicianScheduleRepository;
    private final TechnicianRepository technicianRepository;

    /**
     * Constructor for TechnicianScheduleCommandServiceImpl.
     *
     * @param technicianScheduleRepository the repository for technician schedules
     * @param technicianRepository         the repository for technicians
     */
    public TechnicianScheduleCommandServiceImpl(TechnicianScheduleRepository technicianScheduleRepository,
                                                TechnicianRepository technicianRepository) {
        this.technicianScheduleRepository = technicianScheduleRepository;
        this.technicianRepository = technicianRepository;
    }

    /**
     * Handles the creation of a new Technician Schedule.
     *
     * @param command the command containing schedule information
     * @return the ID of the newly created Technician Schedule
     */
    @Override
    public Long handle(CreateTechnicianScheduleCommand command) {
        try {
            // Verify that the Technician exists
            Optional<Technician> technicianOpt = technicianRepository.findById(command.technician().getId());
            if (technicianOpt.isEmpty()) {
                throw new NotFoundIdException(Technician.class, command.technician().getId());
            }

            Technician technician = technicianOpt.get();

            // Create and save the schedulE
            var technicianSchedule = new TechnicianSchedule(command, technician);
            technicianScheduleRepository.save(technicianSchedule);

            return technicianSchedule.getId();

        } catch (Exception e) {
            throw new PersistenceException(
                    "[CreateTechnicianScheduleCommand] Error while saving technician schedule: " + e.getMessage());
        }
    }

    /**
     * Handles the update of an existing Technician Schedule.
     *
     * @param command the command containing updated schedule data
     * @return an Optional containing the updated Technician Schedule
     */
    @Override
    public Optional<TechnicianSchedule> handle(UpdateTechnicianScheduleCommand command) {
        var technicianScheduleId = command.technicianScheduleId();

        if (!technicianScheduleRepository.existsById(technicianScheduleId)) {
            throw new NotFoundIdException(TechnicianSchedule.class, technicianScheduleId);
        }

        var scheduleToUpdate = technicianScheduleRepository.findById(technicianScheduleId).get();
        scheduleToUpdate.updateTechnicianSchedule(command);

        try {
            technicianScheduleRepository.save(scheduleToUpdate);
            return Optional.of(scheduleToUpdate);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[UpdateTechnicianScheduleCommand] Error while updating technician schedule: " + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a Technician Schedule.
     *
     * @param command the command containing the ID of the Technician Schedule to be deleted
     */
    @Override
    public void handle(DeleteTechnicianScheduleCommand command) {
        var idSchedule = command.technicianScheduleId();

        if (!technicianScheduleRepository.existsById(idSchedule)) {
            throw new NotFoundIdException(TechnicianSchedule.class, idSchedule);
        }

        try {
            technicianScheduleRepository.deleteById(idSchedule);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[DeleteTechnicianScheduleCommand] Error while deleting technician schedule: " + e.getMessage());
        }
    }

}
