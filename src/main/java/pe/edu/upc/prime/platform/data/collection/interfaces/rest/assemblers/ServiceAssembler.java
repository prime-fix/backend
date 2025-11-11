package pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.ServiceResponse;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.UpdateServiceRequest;

public class ServiceAssembler {

    public static CreateServiceCommand toCommandFromRequest(CreateServiceRequest request) {
        return new CreateServiceCommand(
                request.serviceId(),
                request.name(),
                request.description()
        );
    }



    public static UpdateServiceCommand toCommandFromRequest(String serviceId, UpdateServiceRequest request) {
        return new UpdateServiceCommand(
                serviceId,
                request.name(),
                request.description()
        );
    }

    public static ServiceResponse toResponseFromEntity(Service entity){
        return new ServiceResponse(
                entity.getServiceId(),
                entity.getName(),
                entity.getDescription()
        );
    }


}
