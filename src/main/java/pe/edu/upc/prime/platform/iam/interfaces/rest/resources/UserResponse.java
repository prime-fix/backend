package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Response representing a user.
 *
 * @param idUser identifier of the user
 * @param name name of the user
 * @param lastName last name of the user
 * @param dni document number of the user
 * @param phoneNumber phone number of the user
 */
public record UserResponse(String idUser, String name, String lastName,
        String dni, String phoneNumber) {
}
