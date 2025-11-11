package pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects;

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
