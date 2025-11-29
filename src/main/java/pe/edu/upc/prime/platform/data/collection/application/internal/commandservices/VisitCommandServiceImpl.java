package pe.edu.upc.prime.platform.data.collection.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitCommandService;
import pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories.VisitRepository;

/**
 * Implementation of VisitCommandService.
 */
@Service
public class VisitCommandServiceImpl implements VisitCommandService {

    private final VisitRepository visitRepository;

    /**
     * Constructor for VisitCommandServiceImpl.
     * @param visitRepository the visit repository
     */
    public VisitCommandServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Long handle(CreateVisitCommand command) {
        if(this.visitRepository.existsByVehicleId_VehicleId(command.vehicleId().vehicleId())){
            throw new IllegalArgumentException("Vehicle already registered");
        }
        var visit = new Visit(command);
        try{
            this.visitRepository.save(visit);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving visit:"+ e.getMessage());
        }
        return visit.getId();
    }

    @Override
    public void handle(DeleteVisitCommand command) {
        if(!this.visitRepository.existsById(Long.valueOf(command.visitId()))){
            throw new IllegalArgumentException("Error while deleting visit:"+ command.visitId());
        }
        try {
            this.visitRepository.deleteById(Long.valueOf(command.visitId()));
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting visit:"+ e.getMessage());
        }
    }
}
