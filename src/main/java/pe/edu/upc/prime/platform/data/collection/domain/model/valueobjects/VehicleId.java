package pe.edu.upc.center.data_collection.data.domain.model.valueobjects;

public record VehicleId(Long vehicleId) {
    public VehicleId{
        if(vehicleId <0 ){
            throw new IllegalArgumentException("VehicleId has to be positive");
        }
    }
    public Vehicle(){
        this(0L);
    }
}
