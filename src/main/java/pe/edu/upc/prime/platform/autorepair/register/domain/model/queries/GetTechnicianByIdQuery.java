package pe.edu.upc.prime.platform.autorepair.register.domain.model.queries;

/**
 * Query to get a technician by its ID.
 *
 * @param technicianId the ID of the technician
 */
public record GetTechnicianByIdQuery(Long technicianId) {
}
