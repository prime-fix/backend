package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

/**
 * Response for Location
 *
 * @param id  the unique identifier of the location
 * @param address the address of the location
 * @param district the district of the location
 * @param department the department of the location
 */
public record LocationResponse(
        Long id,
        String address,
        String district,
        String department
) {
}
