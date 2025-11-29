package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries;

/**
 * Query to get a service by its ID
 * @param serviceId the ID of the service to retrieve
 */
public record GetServiceByIdQuery(Long serviceId) {
}
