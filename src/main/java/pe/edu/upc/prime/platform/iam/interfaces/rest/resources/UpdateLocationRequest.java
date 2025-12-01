package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request record for updating location information.
 *
 * @param address the address of the location to be updated
 * @param district the district of the location to be updated
 * @param department the department of the location to be updated
 */
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
