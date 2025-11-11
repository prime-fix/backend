package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

import java.util.Objects;

/**
 * Command to update an existing service.
 *
 * @param serviceId The ID of the service to update
 * @param name The name of the visit
 * @param description The description of the Visit
 */
public record UpdateServiceCommand(String serviceId, String name, String description) {

    public UpdateServiceCommand{
        Objects.requireNonNull(serviceId, "[CreateServiceCommand] serviceId is required");
        Objects.requireNonNull(name, "[CreateServiceCommand] name is required");
        Objects.requireNonNull(description, "[CreateServiceCommand] description is required");
    }
}
