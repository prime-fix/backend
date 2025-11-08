package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to create a role.
 *
 * @param idRole the identifier of the role to be created
 * @param name the name of the role to be created
 * @param description the description of the role to be created
 */
public record CreateRoleRequest(
        @NotNull @NotBlank
        @JsonProperty("id_role")
        String idRole,
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String description) {
}
