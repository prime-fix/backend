package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.CreateDiagnosticRequest;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.DiagnosticResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.UpdateDiagnosticRequest;

public class DiagnosticAssembler {

    public static CreateDiagnosisCommand toCommandFromRequest(CreateDiagnosticRequest request) {
        return new CreateDiagnosisCommand(
                new VehicleId(request.vehicleId()),
                request.diagnosis(),
                request.price()
        );
    }

    public static UpdateDiagnosisCommand toCommandFromRequest(Long diagnosticId, UpdateDiagnosticRequest request) {
        return new UpdateDiagnosisCommand(
                diagnosticId,
                request.diagnosis(),
                request.price()
        );
    }

    public static DiagnosticResponse toResourceFromEntity(Diagnostic diagnostic) {
        return new DiagnosticResponse(
                diagnostic.getId().toString(),
                diagnostic.getDiagnosis(),
                diagnostic.getPrice(),
                diagnostic.getVehicleId().vehicleId()
        );
    }


}
