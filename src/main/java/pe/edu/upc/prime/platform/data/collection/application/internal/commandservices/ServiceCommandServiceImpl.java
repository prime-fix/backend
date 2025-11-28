package pe.edu.upc.prime.platform.data.collection.application.internal.commandservices;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.services.ServiceCommandService;
import pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories.ServiceRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of ServiceCommandService.
 */
@org.springframework.stereotype.Service
public class ServiceCommandServiceImpl implements ServiceCommandService {

    private final ServiceRepository serviceRepository;

    /**
     * Constructor for ServiceCommandServiceImpl.
     * @param serviceRepository the service repository
     */
    public ServiceCommandServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public String handle(CreateServiceCommand command) {

        var service = new Service(command);

        try{
            this.serviceRepository.save(service);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving service:"+ e.getMessage());
        }
        return service.getId().toString();
    }

    @Override
    public Optional<Service> handle(UpdateServiceCommand command) {
        var serviceId = command.serviceId();
        if(!this.serviceRepository.existsById(Long.valueOf(serviceId))){
            throw new NotFoundIdException(Service.class, serviceId);
        }
        var serviceUpdate = this.serviceRepository.findById(Long.valueOf(serviceId)).get();
        serviceUpdate.updateService(command);

        try{
            this.serviceRepository.save(serviceUpdate);
            return Optional.of(serviceUpdate);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating service:"+ e.getMessage());
        }
    }

    @Override
    public void handle(DeleteServiceCommand command) {
        if(!this.serviceRepository.existsById(Long.valueOf(command.serviceId()))){
            throw new IllegalArgumentException("Error while deleting service:"+ command.serviceId());
        }
        try {
            this.serviceRepository.deleteById(Long.valueOf(command.serviceId()));
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting service:" + e.getMessage());
        }
    }
}
