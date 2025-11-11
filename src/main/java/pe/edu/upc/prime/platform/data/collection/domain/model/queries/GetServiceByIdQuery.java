package pe.edu.upc.prime.platform.data.collection.domain.model.queries;

/**
 * Query to get a service by its ID
 * @param serviceId the ID of the service to retrieve
 */
public record GetServiceByIdQuery(String serviceId) {
}
