package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.transform;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.TechnicianResource;

public class TechnicianResourceFromEntityAssembler {
    public static TechnicianResource toResourceFromEntity(Technician entity) {
        return new TechnicianResource(
                entity.getId(),
                entity.getName(),
                entity.getSpecialty(),
                entity.getAvailable()
        );
    }
}
