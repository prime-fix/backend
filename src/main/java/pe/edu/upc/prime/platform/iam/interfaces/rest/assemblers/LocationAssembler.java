package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateLocationRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.LocationResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateLocationRequest;

/**
 * Assembler class for converting between Location-related requests, commands, and responses.
 */
public class LocationAssembler {
    /**
     * Convert CreateLocationRequest to CreateLocationCommand.
     *
     * @param request the create location request
     * @return the create location command
     */
    public static CreateLocationCommand toCommandFromRequest(CreateLocationRequest request){
        return new CreateLocationCommand(
                new LocationInformation(
                        request.address(),
                        request.district(),
                        request.department())
        );
    }

    /**
     * Convert UpdateLocationRequest to UpdateLocationCommand.
     *
     * @param locationId the ID of the location to update
     * @param request the update location request
     * @return the update location command
     */
    public static UpdateLocationCommand toCommandFromRequest(Long locationId, UpdateLocationRequest request){
        return new UpdateLocationCommand(
                locationId,
                new LocationInformation(
                        request.address(),
                        request.district(),
                        request.department())
        );
    }

    /**
     * Convert Location entity to LocationResponse.
     *
     * @param entity the location entity
     * @return the location response
     */
    public static LocationResponse toResponseFromEntity(Location entity){
        return new LocationResponse(
                entity.getId(),
                entity.getLocationInformation().address(),
                entity.getLocationInformation().district(),
                entity.getLocationInformation().department()
        );
    }

}
