package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.CreateLocationRequest;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.LocationResponse;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.UpdateLocationRequest;

public class LocationAssembler {

    public static CreateLocationCommand toCommandFromRequest(CreateLocationRequest request){
        return new CreateLocationCommand(
                request.address(),
                request.district(),
                request.department()
        );
    }

    public static UpdateLocationCommand toCommandFromRequest(String locationId, UpdateLocationRequest request){
        return new UpdateLocationCommand(
                locationId,
                request.address(),
                request.district(),
                request.department()
        );
    }

    public static LocationResponse toResponseFRomEntity(Location entity){
        return new LocationResponse(
                entity.getId().toString(),
                entity.getAddress(),
                entity.getDistrict(),
                entity.getDepartment()
        );
    }

}
