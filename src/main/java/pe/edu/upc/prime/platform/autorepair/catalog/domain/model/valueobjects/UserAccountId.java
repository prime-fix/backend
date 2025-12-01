package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a User Account ID
 * @param userAccountId
 */
@Embeddable
public record UserAccountId(Long userAccountId) {

    /**
     * Constructor for UserAccountId with validation
     * @param userAccountId the unique identifier for the profile
     */
    public UserAccountId {
        if (userAccountId < 0) {
            throw new IllegalArgumentException("userAccountId is null or negative");
        }
    }


}
