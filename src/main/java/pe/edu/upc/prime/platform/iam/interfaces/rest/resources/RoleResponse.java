package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response DTO for Role entity.
 *
 * @param idRole the unique identifier of the role
 * @param name the name of the role
 * @param description the description of the role
 */
public record RoleResponse(
        @JsonProperty("id_role") String idRole,
        String name,
        String description) {
}
