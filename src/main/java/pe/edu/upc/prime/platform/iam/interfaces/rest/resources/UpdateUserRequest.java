package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request to update a user.
 *
 * @param name name of the user
 * @param lastName last name of the user
 * @param dni document number of the user
 * @param phoneNumber phone number of the user
 * @param idLocation identifier of the location
 */
public record UpdateUserRequest(
    @NotNull @NotBlank
    @Size(min = 1, max = 100)
    String name,

    @JsonProperty("last_name")
    @NotNull @NotBlank
    @Size(min = 1, max = 100)
    String lastName,

    @NotNull @NotBlank
    @Size(min = 8, max = 8)
    String dni,

    @JsonProperty("phone_number")
    @NotNull @NotBlank
    @Size(min = 7, max = 15)
    String phoneNumber,

    @JsonProperty("id_location")
    @NotNull @NotBlank
    String idLocation) {
}
