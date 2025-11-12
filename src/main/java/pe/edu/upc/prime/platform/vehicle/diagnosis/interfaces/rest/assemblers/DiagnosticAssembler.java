package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resource.CreateDiagnosticRequest;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resource.DiagnosticResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resource.UpdateDiagnosticRequest;

public class DiagnosticAssembler {

    public static CreateDiagnosisCommand toCommandFromRequest(CreateDiagnosticRequest request) {
        return new CreateDiagnosisCommand(
                new VehicleId(request.vehicleId()),
                request.diagnosis(),
                request.price()
        );
    }

    public static UpdateDiagnosisCommand toCommandFromRequest(String id, UpdateDiagnosticRequest request) {
        return new UpdateDiagnosisCommand(
                id,
                request.diagnosis(),
                request.price()
        );
    }

    public static DiagnosticResponse toResourceFromEntity(Diagnostic diagnostic) {
        return new DiagnosticResponse(
                diagnostic.getIdDiagnostic(),
                diagnostic.getDiagnosis(),
                diagnostic.getPrice(),
                diagnostic.getVehicleId().vehicleId()
        );
    }


}
