package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateLocationRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.LocationResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateLocationRequest;

public class LocationAssembler {

    public static CreateLocationCommand toCommandFromRequest(CreateLocationRequest request){
        return new CreateLocationCommand(
                new LocationInformation(
                        request.address(),
                        request.district(),
                        request.department())
        );
    }

    public static UpdateLocationCommand toCommandFromRequest(Long locationId, UpdateLocationRequest request){
        return new UpdateLocationCommand(
                locationId,
                new LocationInformation(
                        request.address(),
                        request.district(),
                        request.department())
        );
    }

    public static LocationResponse toResponseFromEntity(Location entity){
        return new LocationResponse(
                entity.getId(),
                entity.getLocationInformation().address(),
                entity.getLocationInformation().district(),
                entity.getLocationInformation().department()
        );
    }

}
