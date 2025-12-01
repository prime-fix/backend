package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.util.Objects;

/**
 * Command to update an existing service.
 *
 * @param serviceId The ID of the service to update
 * @param name The name of the visit
 * @param description The description of the Visit
 */
public record UpdateServiceCommand(Long serviceId, String name, String description) {

    /**
     * Constructor with validation.
     *
     * @param serviceId the ID of the service to update
     * @param name the name of the visit
     * @param description the description of the Visit
     */
    public UpdateServiceCommand{
        Objects.requireNonNull(serviceId, "[CreateServiceCommand] service id is required");
        Objects.requireNonNull(name, "[CreateServiceCommand] name is required");
        Objects.requireNonNull(description, "[CreateServiceCommand] description is required");
    }
}
