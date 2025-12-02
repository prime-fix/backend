package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

/**
 * Command to delete an Expected Visit by visitId
 * @param visitId the ID of the visitId to be deleted
 */
public record DeleteExpectedVisitByVisitIdCommand(Long visitId) {
}
