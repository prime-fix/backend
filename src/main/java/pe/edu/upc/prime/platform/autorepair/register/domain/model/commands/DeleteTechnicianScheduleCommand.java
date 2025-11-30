package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

/**
 * Command to delete a technician schedule by its identifier.
 *
 * @param technicianScheduleId the identifier of the technician schedule to be deleted
 */
public record DeleteTechnicianScheduleCommand(Long technicianScheduleId) { }

