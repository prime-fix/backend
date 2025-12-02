package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value Object representing the identifier of a User entity.
 *
 * @param userId the unique identifier for the User
 */
@Embeddable
public record UserId(Long userId) {

    /**
     * Constructor with validation.
     *
     * @param userId the unique identifier for the User
     */
    public UserId {
        if (userId < 0) {
            throw new IllegalArgumentException("[UserId] userId cannot be null or negative");
        }
    }

    /**
     * Default constructor for JPA
     */
    public UserId() {
        this(0L);
    }

    /**
     * Returns the user ID value.
     *
     * @return the user ID
     */
    public Long value() {
        return this.userId;
    }
}
