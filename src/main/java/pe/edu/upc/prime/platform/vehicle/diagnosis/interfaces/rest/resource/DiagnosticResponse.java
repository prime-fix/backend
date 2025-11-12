package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resource;

public record DiagnosticResponse(
        String idDiagnostic,
        String diagnosis,
        Float price,
        String vehicleId
) {}