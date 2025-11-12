package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.transform;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.LocationResource;

public class LocationResourceFromEntityAssembler {
    public static LocationResource toResourceFromEntity(Location entity) {
        return new LocationResource(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getOpeningHours()
        );
    }
}
