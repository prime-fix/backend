package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries;

/**
 * Query to check the existence of an auto repair by its ID
 *
 * @param autoRepairId the ID of the auto repair to check
 */
public record ExistsAutoRepairByIdQuery(Long autoRepairId) {
}
