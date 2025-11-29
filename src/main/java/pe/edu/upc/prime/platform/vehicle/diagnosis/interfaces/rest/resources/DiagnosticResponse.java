package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

public record DiagnosticResponse(
        String idDiagnostic,
        String diagnosis,
        Float price,
        String vehicleId
) {}