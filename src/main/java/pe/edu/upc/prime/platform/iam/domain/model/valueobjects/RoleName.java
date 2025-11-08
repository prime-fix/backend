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
    }

    /**
     * Default constructor for JPA.
     */
    public RoleName() {
        this(null);
    }
}
