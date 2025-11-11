package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateServiceRequest(
        @NotNull
        @NotBlank
        @JsonProperty("service_id")
        String serviceId,

        @NotBlank
        @NotNull
        String name,

        @NotBlank
        @NotNull
        String description

)
{
}
