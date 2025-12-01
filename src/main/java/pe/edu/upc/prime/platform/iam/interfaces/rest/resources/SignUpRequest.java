package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

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
