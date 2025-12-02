package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries;

/**
 * Query to check if an Expected Visit exists by its ID.
 *
 * @param expectedVisitId the ID of the Expected Visit to check
 */
public record ExistsExpectedVisitByIdQuery(Long expectedVisitId) {
}
