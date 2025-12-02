package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.commandservices;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.ServiceCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.ServiceRepository;
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

    /**
     * Handles the creation of a new service based on the provided command.
     *
     * @param command the command containing the service information
     * @return the ID of the newly created service
     */
    @Override
    public Long handle(CreateServiceCommand command) {

        var service = new Service(command);

        try{
            this.serviceRepository.save(service);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving service:"+ e.getMessage());
        }
        return service.getId();
    }

    /**
     * Handles the update of an existing service based on the provided command.
     *
     * @param command the command containing the updated service information
     * @return an Optional containing the updated service if successful, or empty if not found
     */
    @Override
    public Optional<Service> handle(UpdateServiceCommand command) {
        var serviceId = command.serviceId();
        if(!this.serviceRepository.existsById(serviceId)){
            throw new NotFoundIdException(Service.class, serviceId);
        }
        var serviceUpdate = this.serviceRepository.findById(serviceId).get();
        serviceUpdate.updateService(command);

        try{
            this.serviceRepository.save(serviceUpdate);
            return Optional.of(serviceUpdate);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating service:"+ e.getMessage());
        }
    }

    /**
     * Handles the deletion of an existing service based on the provided command.
     *
     * @param command the command containing the ID of the service to be related
     */
    @Override
    public void handle(DeleteServiceCommand command) {
        if(!this.serviceRepository.existsById(command.serviceId())){
            throw new IllegalArgumentException("Error while deleting service:"+ command.serviceId());
        }
        try {
            this.serviceRepository.deleteById(command.serviceId());
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting service:" + e.getMessage());
        }
    }
}
