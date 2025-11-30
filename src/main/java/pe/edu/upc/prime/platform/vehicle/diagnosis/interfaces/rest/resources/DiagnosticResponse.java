package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

public record DiagnosticResponse(
        Long id,
        String diagnosis,
        Float price,
        Long vehicleId
) {}