package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Response representing a user.
 *
 * @param id identifier of the user
 * @param name name of the user
 * @param lastName last name of the user
 * @param dni document number of the user
 * @param phoneNumber phone number of the user
 * @param locationId identifier of the location
 */
public record UserResponse(
        Long id,
        String name,
        @JsonProperty("last_name") String lastName,
        String dni,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("location_id") Long locationId) {
}
