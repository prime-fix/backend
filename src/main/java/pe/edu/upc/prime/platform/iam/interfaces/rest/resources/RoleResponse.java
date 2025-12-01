package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

/**
 * Response representing a role.
 *
 * @param id the unique identifier of the role
 * @param name the name of the role
 */
public record RoleResponse(
        Long id,
        String name) {
}
