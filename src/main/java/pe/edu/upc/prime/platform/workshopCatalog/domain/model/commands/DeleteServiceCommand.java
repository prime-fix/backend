package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

/**
 * Command to delete a service by its ID
 * @param serviceId the Id of the service to be deleted
 */
public record DeleteServiceCommand(String serviceId) {
}
