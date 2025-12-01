package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceResponse;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.UpdateServiceRequest;

public class ServiceAssembler {

    public static CreateServiceCommand toCommandFromRequest(CreateServiceRequest request) {
        return new CreateServiceCommand(
                request.name(),
                request.description()
        );
    }



    public static UpdateServiceCommand toCommandFromRequest(Long serviceId, UpdateServiceRequest request) {
        return new UpdateServiceCommand(
                serviceId,
                request.name(),
                request.description()
        );
    }

    public static ServiceResponse toResponseFromEntity(Service entity){
        return new ServiceResponse(
                entity.getId().toString(),
                entity.getName(),
                entity.getDescription()
        );
    }


}
