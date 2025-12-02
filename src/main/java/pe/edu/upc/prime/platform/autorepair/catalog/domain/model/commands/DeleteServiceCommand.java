package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.util.Objects;

/**
 * Command to delete a service by its ID
 * @param serviceId the ID of the service to be deleted
 */
public record DeleteServiceCommand(Long serviceId) {

    /**
     * Constructor with validation.
     *
     * @param serviceId the ID of the service to be deleted
     */
    public DeleteServiceCommand {
        if (Objects.isNull(serviceId) || serviceId <= 0) {
            throw new IllegalArgumentException("[DeleteServiceCommand] Service ID must be a positive number");
        }
    }
}
