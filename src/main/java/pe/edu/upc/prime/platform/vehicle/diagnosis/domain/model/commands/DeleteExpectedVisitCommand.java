package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

/**
 * Command to delete an Expected Visit.
 *
 * @param expectedVisitId the ID of the Expected Visit to be deleted
 */
public record DeleteExpectedVisitCommand(Long expectedVisitId) {
}
