package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing a Vehicle ID.
 *
 * @param vehicleId the unique identifier for the vehicle
 */
@Embeddable
public record VehicleId(Long vehicleId) {
    /**
     * Constructor for IdVehicle with validation.
     *
     * @param vehicleId the unique identifier for the profile
     * @throws IllegalArgumentException if profileId is negative
     */
    public VehicleId {
        if (Objects.isNull(vehicleId) || vehicleId < 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or negative");
        }
    }

    /**
     * Default constructor for IdVehicle with a default value of 0.
     */
    public VehicleId() {
        this(0L);
    }
}
