package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateServiceCommand;

import java.util.Optional;

/**
 * Service interface for handling service-related commands
 */
public interface ServiceCommandService {

    /**
     * Handles the creation of a new service based on the provided command
     * @param command the command containing the service information
     * @return the ID of the newly created service
     */
    String handle(CreateServiceCommand command);

    /**
     * Handles the update of a visit based on the provided command
     * @param command the command containing the updated service information
     * @return an Optional containing the update Service if successful, or empty if not found
     */
    Optional<Service> handle(UpdateServiceCommand command);

    /**
     * Handles the delection of a Service based on the provided command
     * @param command the command containing the ID of the service to be related
     */
    void handle(DeleteServiceCommand command);

}
