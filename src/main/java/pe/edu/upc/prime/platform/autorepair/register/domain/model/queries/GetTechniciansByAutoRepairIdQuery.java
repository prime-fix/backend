package pe.edu.upc.prime.platform.autorepair.register.domain.model.queries;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

/**
 * Query to get all technicians associated with a specific AutoRepair.
 *
 * @param autoRepairId the ID of the auto repair
 */
public record GetTechniciansByAutoRepairIdQuery(AutoRepairId autoRepairId) {
}
