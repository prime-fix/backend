package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

/**
 * Request to create a new Rating.
 *
 * @param starRating   The number of stars (1-5).
 * @param comment      The optional comment of the user.
 * @param idAutoRepair The ID of the auto repair being rated.
 * @param idUserAccount The ID of the user account that creates the rating.
 */
public record CreateRatingRequest(


        @JsonProperty("star_rating")
        @Min(1) @Max(5)
        int starRating,

        @JsonProperty("comment")
        @Size(max = 250, message = "Comment cannot exceed 250 characters")
        String comment,

        @JsonProperty("auto_repair_id")
        @NotNull @NotBlank
        Long autoRepairId,

        @JsonProperty("user_account_id")
        @NotNull @NotBlank
        Long userAccountId
) {
}
