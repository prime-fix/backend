package pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects;

/**
 * Value object representing a vehicle
 * @param vehicleId the vehicleID
 */
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
