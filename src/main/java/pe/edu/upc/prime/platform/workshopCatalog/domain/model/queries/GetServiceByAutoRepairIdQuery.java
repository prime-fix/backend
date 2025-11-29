package pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries;

import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.AutoRepairId;

/**
 * Query to get a service by autoRepair ID
 *
 * @param autoRepairId the autoRepair id
 */
public record GetServiceByAutoRepairIdQuery(Long autoRepairId) {
}
