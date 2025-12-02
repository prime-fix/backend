package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.ExpectedVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.StateVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VisitId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.CreateExpectedVisitRequest;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.ExpectedVisitResponse;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources.UpdateExpectedVisitRequest;

/**
 * Assembler for converting between ExpectedVisit-related requests, commands, and responses.
 */
public class ExpectedVisitAssembler {
    /**
     * Converts a CreateExpectedVisitRequest to a CreateExpectedVisitCommand.
     *
     * @param request the CreateExpectedVisitRequest to convert
     * @return the corresponding CreateExpectedVisitCommand
     */
    public static CreateExpectedVisitCommand toCommandFromRequest(CreateExpectedVisitRequest request) {
        return new CreateExpectedVisitCommand(
                new VisitId(request.visitId()),
                new VehicleId(request.vehicleId())
        );
    }

    /**
     * Converts an UpdateExpectedVisitRequest to an UpdateExpectedVisitCommand.
     *
     * @param expectedVisitId the ID of the expected visit to update
     * @param request the UpdateExpectedVisitRequest to convert
     * @return the corresponding UpdateExpectedVisitCommand
     */
    public static UpdateExpectedVisitCommand toCommandFromRequest(Long expectedVisitId, UpdateExpectedVisitRequest request) {
        return new UpdateExpectedVisitCommand(
                expectedVisitId,
                StateVisit.valueOf(request.stateVisit().toUpperCase()),
                new VisitId(request.visitId()),
                request.isScheduled(),
                new VehicleId(request.vehicleId())
        );
    }

    /**
     * Converts an ExpectedVisit entity to an ExpectedVisitResponse.
     *
     * @param entity the ExpectedVisit entity to convert
     * @return the corresponding ExpectedVisitResponse
     */
    public static ExpectedVisitResponse toResourceFromEntity(ExpectedVisit entity) {
        return new ExpectedVisitResponse(
                entity.getId(),
                entity.getStateVisit().name(),
                entity.getVisitId().value(),
                entity.getIsScheduled(),
                entity.getVehicleId().value()
        );
    }

    /**
     * Converts raw values to a CreateExpectedVisitCommand.
     *
     * @param visitId the ID of the visit
     * @return the corresponding CreateExpectedVisitCommand
     */
    public static CreateExpectedVisitCommand toCommandFromValues(Long visitId, Long vehicleId) {
        return new CreateExpectedVisitCommand(
                new VisitId(visitId),
                new VehicleId(vehicleId)
        );
    }
}
