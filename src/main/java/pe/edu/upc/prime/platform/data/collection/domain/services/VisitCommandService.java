package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteVisitCommand;

/**
 * Service interface for handling visit-related commands.
 */
public interface VisitCommandService {

    /**
     * Handles the creation of a new visit based on the provided command.
     *
     * @param command the command containing the visit information
     * @return the ID of the newly created visit
     */
    Long handle(CreateVisitCommand command);

    /**
     * Handles the deletion of a visit based on the provided command.
     * @param command the command containing the ID of the visit to be deleted
     */
    void handle(DeleteVisitCommand command);
}
