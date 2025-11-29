package pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.ServiceId;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateVisitRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;

public class VisitAssembler {

    public static CreateVisitCommand toCommandFromRequest(CreateVisitRequest request) {
        return new CreateVisitCommand(
                request.failure(),
                new VehicleId(request.vehicleId()),
                request.timeVisit(),
                new AutoRepairId(request.autoRepairId()),
                new ServiceId(request.serviceId())
        );
    }

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
