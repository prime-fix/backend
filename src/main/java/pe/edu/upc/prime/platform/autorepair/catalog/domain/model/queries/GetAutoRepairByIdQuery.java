package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries;

/**
 * Query to get a AutoRepair by their ID
 * @param repairId the ID of the AutoRepair to retrieve
 */
public record GetAutoRepairByIdQuery(Long repairId) {}