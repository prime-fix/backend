package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

/**
 * Request to update an existing Rating.
 *
 * @param starRating The new star rating (1-5).
 * @param comment    The new comment. Maximum 250 characters.
 * @param autoRepairId The ID of the auto repair being rated.
 * @param userAccountId The user account ID linked to this rating.
 */
public record UpdateRatingRequest(
        @JsonProperty("star_rating")
        @Min(1) @Max(5)
        Integer starRating,

        @JsonProperty("comment")
        @Size(max = 250, message = "Comment cannot exceed 250 characters")
        String comment,

        @JsonProperty("auto_repair_id")
        @NotNull @NotBlank
        Long autoRepairId,

        @JsonProperty("user_account_id")
        @NotNull @NotBlank
        Long userAccountId) {
}
