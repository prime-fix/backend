package pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

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
        if (Objects.isNull(userAccountId) || userAccountId <= 0) {
            throw new IllegalArgumentException("userAccountId is null or negative");
        }
    }

    /**
     * Default constructor for UserAccountId with a default value of 0
     */
    public UserAccountId(){
        this(0L);
    }

}
