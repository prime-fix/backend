package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.util.Objects;

/**
 * Command to create a new Service
 *
 * @param name The name of the Service.
 * @param description The description on the Service.
 */
public record CreateServiceCommand( String name, String description) {

    /**
     * Constructor with validation.
     *
     * @param name the name of the Service
     * @param description the description of the Service
     */
    public CreateServiceCommand{
        Objects.requireNonNull(name, "[CreateServiceCommand] name is required");
        Objects.requireNonNull(description, "[CreateServiceCommand] description is required");
    }

}
