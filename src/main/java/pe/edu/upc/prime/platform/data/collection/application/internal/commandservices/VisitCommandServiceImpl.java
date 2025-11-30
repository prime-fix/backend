package pe.edu.upc.prime.platform.data.collection.application.internal.commandservices;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.domain.exceptions.VisitAlreadyRegisteredException;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.events.VisitCreatedEvent;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitCommandService;
import pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories.VisitRepository;

/**
 * Implementation of VisitCommandService.
 */
@Service
public class VisitCommandServiceImpl implements VisitCommandService {

    private final VisitRepository visitRepository;
    private final ApplicationEventPublisher publisher;
    /**
     * Constructor for VisitCommandServiceImpl.
     * @param visitRepository the visit repository
     */
    public VisitCommandServiceImpl(VisitRepository visitRepository, ApplicationEventPublisher publisher) {
        this.visitRepository = visitRepository;
        this.publisher = publisher;
    }

    /**
     * Handles the creation of a new Visit based on the command
     * @param command the command containing the visit information
     * @return The ID of the newly created Visit
     */
    @Override
    public Long handle(CreateVisitCommand command) {
        Long vehicleId = command.vehicleId().vehicleId();

        if(this.visitRepository.existsByAutoRepairId_AutoRepairId(command.autoRepairId().autoRepairId())){
            throw new VisitAlreadyRegisteredException(vehicleId, command.autoRepairId().autoRepairId());
        }
        var visit = new Visit(command);
        try{
            this.visitRepository.save(visit);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving visit:"+ e.getMessage());
        }
        var event = new VisitCreatedEvent(
                this,
                visit.getId(),
                visit.getVehicleId().vehicleId(),
                visit.getAutoRepairId().autoRepairId(),
                visit.getServiceId().serviceId()
        );
        this.publisher.publishEvent(event);
        return visit.getId();
    }

    /**
     * Handles the deletion of an existing Visit by its ID
     * @param command the command containing the ID of the visit to be deleted
     */
    @Override
    public void handle(DeleteVisitCommand command) {
        if(!this.visitRepository.existsById(command.visitId())){
            throw new IllegalArgumentException("Error while deleting visit:"+ command.visitId());
        }
        try {
            this.visitRepository.deleteById(command.visitId());
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting visit:"+ e.getMessage());
        }
    }
}
