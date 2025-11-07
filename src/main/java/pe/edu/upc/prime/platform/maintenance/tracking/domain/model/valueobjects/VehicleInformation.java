package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing vehicle information.
 *
 * @param vehicleBrand the brand of the vehicle
 * @param vehiclePlate the license plate of the vehicle
 * @param vehicleType the type of the vehicle
 */
@Embeddable
public record VehicleInformation(String vehicleBrand, String vehiclePlate, String vehicleType) {

    public VehicleInformation {
        if (Objects.isNull(vehicleBrand) || vehicleBrand.isBlank()) {
            throw new IllegalArgumentException("[VehicleInformation] Vehicle brand cannot be null or blank");
        }
        if (Objects.isNull(vehiclePlate) || vehiclePlate.isBlank()) {
            throw new IllegalArgumentException("[VehicleInformation] Vehicle plate cannot be null or blank");
        }
        if (Objects.isNull(vehicleType) || vehicleType.isBlank()) {
            throw new IllegalArgumentException("[VehicleInformation] Vehicle type cannot be null or blank");
        }
    }

    /**
     * Default constructor for JPA.
     */
    public VehicleInformation() {
        this(null, null, null);
    }

    /**
     * Returns a formatted string with vehicle details.
     *
     * @return a string containing vehicle brand, plate, and type
     */
    public String getVehicleDetails() {
        return String.format("Brand: %s, Plate: %s, Type: %s", vehicleBrand, vehiclePlate, vehicleType);
    }
}
