package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to create a new location.
 *
 * @param address the address of the location to be created
 * @param district the district of the location  to be created
 * @param department the department of the location to be created
 */
public record CreateLocationRequest(
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
