package pe.edu.upc.prime.platform.data.collection.domain.model.queries;

/**
 * Query to get a Visit by its ID
 * @param visitId the ID of the visit to retrieve
 */
public record GetVisitByIdQuery(Long visitId) {
}
