package pe.edu.upc.prime.platform.autorepair.register.domain.model.queries;

/**
 * Query to get all technicians associated with a specific AutoRepair.
 *
 * @param idAutoRepair the ID of the auto repair
 */
public record GetTechniciansByAutoRepairIdQuery(Long idAutoRepair) {
}
