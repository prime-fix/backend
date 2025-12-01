package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceResponse;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.UpdateServiceRequest;

/**
 * Assembler for converting between Service-related requests, commands, and responses.
 */
public class ServiceAssembler {
    /**
     * Converts a CreateServiceRequest to a CreateServiceCommand.
     *
     * @param request the CreateServiceRequest to convert
     * @return the corresponding CreateServiceCommand
     */
    public static CreateServiceCommand toCommandFromRequest(CreateServiceRequest request) {
        return new CreateServiceCommand(
                request.name(),
                request.description()
        );
    }

    /**
     * Converts an UpdateServiceRequest to an UpdateServiceCommand.
     *
     * @param serviceId the ID of the service to update
     * @param request the UpdateServiceRequest to convert
     * @return the corresponding UpdateServiceCommand
     */
    public static UpdateServiceCommand toCommandFromRequest(Long serviceId, UpdateServiceRequest request) {
        return new UpdateServiceCommand(
                serviceId,
                request.name(),
                request.description()
        );
    }

    /**
     * Converts a Service entity to a ServiceResponse.
     *
     * @param entity the Service entity to convert
     * @return the corresponding ServiceResponse
     */
    public static ServiceResponse toResponseFromEntity(Service entity){
        return new ServiceResponse(
                entity.getId().toString(),
                entity.getName(),
                entity.getDescription()
        );
    }


}
