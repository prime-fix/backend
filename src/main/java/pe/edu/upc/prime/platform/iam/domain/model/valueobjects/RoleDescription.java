package pe.edu.upc.prime.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing a role description.
 *
 * @param description the description of the role
 */
@Embeddable
public record RoleDescription(String description) {
    public RoleDescription {
        if (Objects.isNull(description) || description.isBlank()) {
            throw new IllegalArgumentException("[RoleDescription] Description cannot be null or blank");
        }
    }

    /**
     * Default constructor for JPA.
     */
    public RoleDescription() {
        this(null);
    }
}
