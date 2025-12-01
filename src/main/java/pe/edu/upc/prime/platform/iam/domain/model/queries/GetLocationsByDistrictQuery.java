package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get locations by district.
 *
 * @param district the district to search for
 */
public record GetLocationsByDistrictQuery(String district) {
}
