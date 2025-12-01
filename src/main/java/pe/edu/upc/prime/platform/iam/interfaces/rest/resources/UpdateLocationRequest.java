package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLocationRequest(
        @NotBlank
        @NotNull
        String address,

        @NotBlank
        @NotNull
        String district,

        @NotBlank
        @NotNull
        String department
) {
}
