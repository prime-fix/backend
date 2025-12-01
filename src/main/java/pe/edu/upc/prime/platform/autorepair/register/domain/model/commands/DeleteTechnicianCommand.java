package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

/**
 * Command to delete a technician by ID.
 *
 * @param technicianId the ID of the technician to be deleted
 */
public record DeleteTechnicianCommand(Long technicianId ) {
}
