package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

/**
 * Command to delete a service to the auto repair service catalog
 * @param serviceId The service to delete
 */
public record DeleteServiceToAutoRepairServiceCatalogCommand(Long serviceId, Long autoRepairId) {

}
