package pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.ServiceId;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateVisitRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

/**
 * Assembler for converting between Visit-related requests, commands, and responses.
 */
public class VisitAssembler {

    /**
     * Converts a CreateVisitRequest to a CreateVisitCommand.
     *
     * @param request the CreateVisitRequest to convert
     * @return the corresponding CreateVisitCommand
     */
    public static CreateVisitCommand toCommandFromRequest(CreateVisitRequest request) {
        return new CreateVisitCommand(
                request.failure(),
                new VehicleId(request.vehicleId()),
                request.timeVisit(),
                new AutoRepairId(request.autoRepairId()),
                new ServiceId(request.serviceId())
        );
    }

    /**
     * Converts a Visit entity to a VisitResponse.
     *
     * @param visit the Visit entity to convert
     * @return the corresponding VisitResponse
     */
    public static VisitResponse toResponseFromEntity(Visit visit) {
        return new VisitResponse(
                visit.getId().toString(),
                visit.getFailure(),
                visit.getVehicleId().vehicleId(),
                visit.getTimeVisit(),
                visit.getAutoRepairId().autoRepairId(),
                visit.getServiceId().serviceId());
    }

}
