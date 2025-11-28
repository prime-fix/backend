package pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateVisitRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.UpdateServiceRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;

public class VisitAssembler {

    public static CreateVisitCommand toCommandFromRequest(CreateVisitRequest request) {
        return new CreateVisitCommand(
                request.failure(),
                request.vehicleId(),
                request.timeVisit(),
                request.autoRepairId(),
                request.serviceId()
        );
    }

    public static VisitResponse toResponseFromEntity(Visit visit) {
        return new VisitResponse(
                visit.getId().toString(),
                visit.getFailure(),
                visit.getVehicleId(),
                visit.getTimeVisit(),
                visit.getAutoRepairId(),
                visit.getServiceId()
        );
    }

}
