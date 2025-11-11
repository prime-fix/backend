package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

import org.aspectj.bridge.IMessage;

import java.util.Objects;

/**
 * Command to create a new Service
 *
 * @param serviceId The Id of the Service.
 * @param name The name of the Service.
 * @param description The description on the Service.
 */
public record CreateServiceCommand(String serviceId, String name, String description) {

    public CreateServiceCommand{
        Objects.requireNonNull(serviceId, "[CreateServiceCommand] serviceId is required");
        Objects.requireNonNull(name, "[CreateServiceCommand] name is required");
        Objects.requireNonNull(description, "[CreateServiceCommand] description is required");
    }

}
