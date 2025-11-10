package pe.edu.upc.prime.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing a role name.
 *
 * @param name the name of the role
 */
@Embeddable
public record RoleName(String name) {
    public RoleName {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("[RoleName] Name cannot be null or blank");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("[RoleName] Name cannot exceed 50 characters");
        }
    }

    /**
     * Default constructor for JPA.
     */
    public RoleName() {
        this(null);
    }
}
