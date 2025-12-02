package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.ExpectedVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateExpectedVisitCommand;

import java.util.Optional;

/**
 * Service interface for handling expected visit commands.
 *
 */
public interface ExpectedVisitCommandService {
    /**
     * Handles the creation of a new expected visit based on the provided command.
     *
     * @param command the command containing the expected visit information
     * @return the ID of the newly created ExpectedVisit
     */
    Long handle(CreateExpectedVisitCommand command);

    /**
     * Handles the update of an existing expected visit based on the provided command.
     *
     * @param command the command containing the updated expected visit information
     * @return an Optional containing the updated ExpectedVisit if the update was successful, or empty if not found
     */
    Optional<ExpectedVisit> handle(UpdateExpectedVisitCommand command);

    /**
     * Handles the deletion of an expected visit based on the provided command.
     *
     * @param command the command containing the ID of the expected visit to be deleted
     */
    void handle(DeleteExpectedVisitCommand command);
}
