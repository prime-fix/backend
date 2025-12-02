package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

public record AutoRepairSignUpRequest(
        @NotNull @NotBlank
        @JsonProperty("auto_repair_name")
        String autoRepairName,

        @NotNull @NotBlank
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull @NotBlank
        String username,

        @NotNull @NotBlank
        String email,

        @NotNull @NotBlank
        String password,

        @NotBlank
        @NotNull
        @JsonProperty("contact_email")
        String contactEmail,

        @NotBlank
        @NotNull
        String ruc,

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
        @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits")
        String cardNumber,

        @NotNull @NotBlank
        @JsonProperty("card_type")
        @Pattern(regexp = "VISA|MASTERCARD|AMEX", message = "Card type must be VISA, MASTERCARD or AMEX")
        String cardType,

        @NotNull
        @Min(1) @Max(12)
        Integer month,

        @NotNull
        @Min(2000)
        Integer year,

        @NotNull
        @Min(100) @Max(9999)
        Integer cvv) {
}
