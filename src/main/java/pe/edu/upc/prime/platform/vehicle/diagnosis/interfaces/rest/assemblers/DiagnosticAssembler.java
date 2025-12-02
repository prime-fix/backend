package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.CreateDiagnosticRequest;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.DiagnosticResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.UpdateDiagnosticRequest;

/**
 * Assembler for converting between Diagnostic-related requests, commands, and responses.
 */
public class DiagnosticAssembler {
    /**
     * Converts a CreateDiagnosticRequest to a CreateDiagnosisCommand.
     *
     * @param request the CreateDiagnosticRequest to convert
     * @return the corresponding CreateDiagnosisCommand
     */
    public static CreateDiagnosticCommand toCommandFromRequest(CreateDiagnosticRequest request) {
        return new CreateDiagnosticCommand(
                request.price(),
                new VehicleId(request.vehicleId()),
                request.diagnosis(),
                request.expectedVisitId()
        );
    }

    /**
     * Converts an UpdateDiagnosticRequest to an UpdateDiagnosisCommand.
     *
     * @param diagnosticId the ID of the diagnostic to update
     * @param request the UpdateDiagnosticRequest to convert
     * @return the corresponding UpdateDiagnosisCommand
     */
    public static UpdateDiagnosticCommand toCommandFromRequest(Long diagnosticId, UpdateDiagnosticRequest request) {
        return new UpdateDiagnosticCommand(
                diagnosticId,
                request.price(),
                new VehicleId(request.vehicleId()),
                request.diagnosis(),
                request.expectedVisitId()
        );
    }

    /**
     * Converts a Diagnostic entity to a DiagnosticResponse.
     *
     * @param diagnostic the Diagnostic entity to convert
     * @return the corresponding DiagnosticResponse
     */
    public static DiagnosticResponse toResourceFromEntity(Diagnostic diagnostic) {
        return new DiagnosticResponse(
                diagnostic.getId(),
                diagnostic.getDiagnosis(),
                diagnostic.getPrice(),
                diagnostic.getVehicleId().value(),
                diagnostic.getExpectedVisit().getId()
        );
    }
}
