package pe.edu.upc.prime.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value Object that represents the location information of a user.
 *
 * @param address the address of the user
 * @param district the district of the user
 * @param department the department of the user
 */
@Embeddable
public record LocationInformation(String address, String district, String department) {

    /**
     * Constructor with validation
     *
     * @param address the address of the user
     * @param district the district of the user
     * @param department the department of the user
     */
    public LocationInformation {
        if (Objects.isNull(address) || address.isBlank()) {
            throw new IllegalArgumentException("[LocationInformation] Address cannot be null or blank");
        }
        if (Objects.isNull(district) || district.isBlank()) {
            throw new IllegalArgumentException("[LocationInformation] District cannot be null or blank");
        }
        if (Objects.isNull(department) || department.isBlank()) {
            throw new IllegalArgumentException("[LocationInformation] Department cannot be null or blank");
        }
    }

    /**
     * Default constructor for JPA
     */
    public LocationInformation() {
        this(null, null, null);
    }
}
