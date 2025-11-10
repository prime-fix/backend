package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to update a role.
 *
 * @param name the name of the role to be updated
 * @param description the description of the role to be updated
 */
public record UpdateRoleRequest(
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String description) {
}
