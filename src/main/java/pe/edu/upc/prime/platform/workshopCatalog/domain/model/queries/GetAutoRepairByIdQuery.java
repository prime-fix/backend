package pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries;

/**
 * Query to get a AutoRepair by their ID
 * @param repairId the ID of the AutoRepair to retrieve
 */
public record GetAutoRepairByIdQuery(String repairId) {}