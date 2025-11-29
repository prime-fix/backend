package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries;

/**
 *  Query to get a Location by their ID
 * @param locationId the ID of the Location to retrieve
 */
public record GetLocationByIdQuery(Long locationId) {
}
