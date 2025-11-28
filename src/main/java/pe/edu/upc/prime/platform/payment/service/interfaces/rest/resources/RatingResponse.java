package pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resource representation of a Rating.
 *
 * @param idRating      The unique identifier of the rating.
 * @param starRating    The number of stars (1-5).
 * @param comment       The comment associated with the rating.
 * @param idAutoRepair  The ID of the auto repair being rated.
 * @param idUserAccount The ID of the user who created the rating.
 */
public record RatingResponse(
        @JsonProperty("id_rating") String idRating,
        @JsonProperty("star_rating") int starRating,
        String comment,
        @JsonProperty("id_user_repair") String idAutoRepair,
        @JsonProperty("id_user_account") String idUserAccount
) {
}
