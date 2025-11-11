package pe.edu.upc.prime.platform.vehiclediagnosis.interfaces.rest.resource;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects.VehicleId;

public record DiagnosticResponse(
        String idDiagnostic,
        String diagnosis,
        Float price,
        String vehicleId
) {}