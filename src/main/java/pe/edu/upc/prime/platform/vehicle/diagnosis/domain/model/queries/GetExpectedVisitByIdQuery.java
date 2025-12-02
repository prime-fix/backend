package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries;

/**
 * Query to get an expected visit by its ID.
 *
 * @param expectedVisitId the ID of the expected visit
 */
public record GetExpectedVisitByIdQuery(Long expectedVisitId) {
}
