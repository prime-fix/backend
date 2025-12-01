package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resource representation of a Rating.
 *
 * @param id      The unique identifier of the rating.
 * @param starRating    The number of stars (1-5).
 * @param comment       The comment associated with the rating.
 * @param autoRepairId  The ID of the auto repair being rated.
 * @param userAccountId The ID of the user who created the rating.
 */
public record RatingResponse(
        Long id,
        @JsonProperty("star_rating") Integer starRating,
        String comment,
        @JsonProperty("auto_repair_id") Long autoRepairId,
        @JsonProperty("user_account_id") Long userAccountId
) {
}
