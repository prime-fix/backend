package pe.edu.upc.prime.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing a membership description.
 *
 * @param description the description of the membership
 */
@Embeddable
public record MembershipDescription(String description) {

    public MembershipDescription {
        if (Objects.isNull(description) || description.isBlank()) {
            throw new IllegalArgumentException("[MembershipDescription] Description cannot be null or blank");
        }
    }

    /**
     * Default constructor for JPA.
     */
    public MembershipDescription() {
        this(null);
    }
}
