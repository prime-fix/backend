package pe.edu.upc.center.data_collection.data.domain.model.valueobjects;

public record AutoRepairId(Long autoRepairId) {

    public AutoRepairId{
        if(autoRepairId <0){
            throw new IllegalArgumentException("Auto Repair IdAutoRepair cannot be negative");
        }
    }

    public AutoRepairId(){
        this(0L);
    }
}
