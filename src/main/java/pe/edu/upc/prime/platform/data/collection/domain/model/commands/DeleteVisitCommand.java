package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

/**
 * Command to delete a visit by its ID
 * @param visitId the ID of the visit to be deleted
 */
public record DeleteVisitCommand(Long visitId) {
}
