package pe.edu.upc.prime.platform.autorepair.register.domain.model.queries;

/**
 * Query to get a technician by its ID.
 *
 * @param idTechnician the ID of the technician
 */
public record GetTechnicianByIdQuery(String idTechnician) {
}
