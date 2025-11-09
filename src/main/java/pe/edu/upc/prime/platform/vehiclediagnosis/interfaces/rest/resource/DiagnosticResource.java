package pe.edu.upc.prime.platform.vehiclediagnosis.interfaces.rest.resource;

public record DiagnosticResource(
        String idDiagnostic,
        String vehicleId,
        String diagnosis,
        Float price
) {}