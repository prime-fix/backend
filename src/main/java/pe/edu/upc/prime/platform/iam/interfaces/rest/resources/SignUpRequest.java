package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

/**
 * Request record for user sign-up.
 *
 * @param name the user's first name
 * @param lastName the user's last name
 * @param dni the user's DNI (identification number)
 * @param phoneNumber the user's phone number
 * @param username the user's username
 * @param email the user's email address
 * @param roleId the ID of the user's role
 * @param password the user's password
 * @param address the user's address
 * @param district the user's district
 * @param department the user's department
 * @param membershipDescription the description of the user's membership
 * @param started the start date of the membership
 * @param over the end date of the membership
 * @param cardNumber the user's card number
 * @param cardType the type of the user's card
 * @param month the expiration month of the card
 * @param year the expiration year of the card
 * @param cvv the CVV of the card
 */
public record SignUpRequest(
        @NotNull @NotBlank
        String name,

        @NotNull @NotBlank
        @JsonProperty("last_name")
        String lastName,

        @NotNull @NotBlank
        String dni,

        @NotNull @NotBlank
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull @NotBlank
        String username,

        @NotNull @NotBlank
        String email,

        @NotNull
        @JsonProperty("role_id")
        Long roleId,

        @NotNull @NotBlank
        String password,

        @NotNull @NotBlank
        String address,

        @NotNull @NotBlank
        String district,

        @NotNull @NotBlank
        String department,

        @NotNull @NotBlank
        @JsonProperty("membership_description")
        String membershipDescription,

        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        LocalDate started,

        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        LocalDate over,

        @NotNull @NotBlank
        @JsonProperty("card_number")
        String cardNumber,

        @NotNull
        @JsonProperty("card_type")
        String cardType,

        @NotNull
        @Min(1) @Max(12)
        Integer month,

        @NotNull
        Integer year,

        @NotNull
        Integer cvv) {
}
