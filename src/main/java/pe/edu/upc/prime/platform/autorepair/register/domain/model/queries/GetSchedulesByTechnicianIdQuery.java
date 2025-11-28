package pe.edu.upc.prime.platform.autorepair.register.domain.model.queries;

/**
 * Query to get all schedules for a specific technician.
 *
 * @param idTechnician the ID of the technician
 */
public record GetSchedulesByTechnicianIdQuery(String idTechnician) {
}
