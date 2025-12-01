package pe.edu.upc.prime.platform.autorepair.register.domain.model.queries;

/**
 * Query to get a technician schedule by its ID.
 *
 * @param technicianScheduleId the ID of the technician schedule
 */
public record GetTechnicianScheduleByIdQuery(Long technicianScheduleId) {
}
