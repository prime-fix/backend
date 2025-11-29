package pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a vehicle
 * @param vehicleId the vehicleID
 */
@Embeddable
public record VehicleId(Long vehicleId) {
    public VehicleId{
        if(vehicleId<0){
            throw new IllegalArgumentException("VehicleId has to be positive");
        }
    }

    public VehicleId(){
        this(0L);
    }

}
