package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new service.
 * @param name the name of the service
 * @param description the description of the service
 */
public record CreateServiceRequest(

        @NotBlank
        @NotNull
        String name,

        @NotBlank
        @NotNull
        String description

)
{
}
