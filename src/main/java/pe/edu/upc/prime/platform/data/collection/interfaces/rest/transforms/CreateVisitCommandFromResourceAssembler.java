package pe.edu.upc.center.data_collection.data.interfaces.rest.transforms;

import pe.edu.upc.center.data_collection.data.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.center.data_collection.data.interfaces.rest.resources.CreateVisitResource;
import pe.edu.upc.center.data_collection.data.interfaces.rest.resources.VisitResource;

public class CreateVisitCommandFromResourceAssembler {
    public static CreateVisitCommand toCommandFromResource(CreateVisitResource resource) {
        return new CreateVisitCommand(resource.idVehicle(), resource.service(),
                resource.failure(),resource.timeVisit(),
                resource.idAutoRepair());
    }
}
