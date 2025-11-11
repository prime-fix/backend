package pe.edu.upc.prime.platform.data.collection.domain.model.queries;

/**
 * Query to get a service by autoRepair ID
 *
 * @param autoRepairId the autoRepair id
 */
public record GetServiceByAutoRepairIdQuery(String autoRepairId) {
}
