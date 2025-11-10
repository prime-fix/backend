package pe.edu.upc.prime.platform.workshop.catalog.domain.model.valueObjects;

public record AutoRepairStatus(String status) {
    public AutoRepairStatus {
        if (!status.matches("PENDING|ACCEPTED|REJECTED|COMPLETED")) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
