package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

/**
 * Request to update an existing Rating.
 *
 * @param id         The ID of the rating to update.
 * @param starRating The new star rating (1-5).
 * @param comment    The new comment.
 */

public record UpdateRatingRequest(
        @JsonProperty("id")
        @NotNull @NotBlank
        String id,

        @JsonProperty("starRating")
        @Min(1) @Max(5)
        int starRating,

        @JsonProperty("comment")
        @Size(max = 250, message = "Comment cannot exceed 250 characters")
        String comment
) {
}
