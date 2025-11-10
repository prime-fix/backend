package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing a Visit ID.
 *
 * @param visitId the unique identifier for the Visit
 */
@Embeddable
public record VisitId(String visitId) {
    /**
     * Constructor for IdVisit with validation.
     *
     * @param visitId the unique identifier for the visit
     * @throws IllegalArgumentException if IdVisit is negative
     */
    public VisitId {
        if (Objects.isNull(visitId) || visitId.isBlank()) {
            throw new IllegalArgumentException("Visit ID cannot be null or negative");
        }
    }

    /**
     * Default constructor for IdVehicle with a default value of 0.
     */
    public VisitId() {
        this(null);
    }
}
