package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing a Visit ID.
 *
 * @param visitId the unique identifier for the Visit
 */
@Embeddable
public record VisitId(Long visitId) {
    /**
     * Constructor for IdVisit with validation.
     *
     * @param visitId the unique identifier for the visit
     */
    public VisitId {
        if (Objects.isNull(visitId) || visitId <= 0) {
            throw new IllegalArgumentException("Visit ID cannot be null or negative");
        }
    }

    /**
     * Default constructor for IdVehicle with a default value of 0.
     */
    public VisitId() {
        this(null);
    }

    /**
     * Gets the value of the visitId.
     *
     * @return the visitId
     */
    public Long value() {
        return this.visitId;
    }
}
