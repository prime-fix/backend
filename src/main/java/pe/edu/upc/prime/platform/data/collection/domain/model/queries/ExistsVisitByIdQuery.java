package pe.edu.upc.prime.platform.data.collection.domain.model.queries;

/**
 * Query to check the existence of a Visit by its ID.
 *
 * @param visitId the ID of the Visit to check
 */
public record ExistsVisitByIdQuery(Long visitId) {
}
