package pe.edu.upc.center.data_collection.data.interfaces.rest.transforms;

import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.interfaces.rest.resources.VisitResource;

public class VisitResourceFromEntityAssembler {
    public static VisitResource toResourceFromEntity(Visit entity) {
        return new VisitResource(
                entity.getId(),entity.getIdVehicle(), entity.getService(),
                entity.getFailure(),entity.getTimeVisit(),
                entity.getIdAutoRepair()
        );
    }

}
