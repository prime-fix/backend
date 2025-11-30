package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response DTO for Role entity.
 *
 * @param id the unique identifier of the role
 * @param name the name of the role
 */
public record RoleResponse(
        Long id,
        String name) {
}
