package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.transform;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.CreateAutoRepairResource;

public class CreateAutoRepairCommandFromResourceAssembler {
    public static CreateAutoRepairCommand toCommandFromResource(CreateAutoRepairResource resource) {
        return new CreateAutoRepairCommand(resource.customerName(), resource.repairDate(), resource.repairTime(), resource.description());
    }
}
