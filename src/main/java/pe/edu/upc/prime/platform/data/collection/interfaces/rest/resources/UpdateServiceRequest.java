package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateServiceRequest(
        @NotBlank
        @NotNull
        String name,

        @NotBlank
        @NotNull
        String description) {
}
