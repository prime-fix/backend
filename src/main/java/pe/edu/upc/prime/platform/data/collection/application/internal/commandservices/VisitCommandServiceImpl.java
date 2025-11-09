package pe.edu.upc.center.data_collection.data.application.internal.commandservices;


import org.springframework.stereotype.Service;
import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.center.data_collection.data.domain.services.VisitCommandService;
import pe.edu.upc.center.data_collection.data.infrastructure.persistance.jpa.repositories.VisitRepository;

@Service
public class VisitCommandServiceImpl implements VisitCommandService {

    private final VisitRepository visitRepository;
    public VisitCommandServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Long handle(CreateVisitCommand command) {

        if (this.visitRepository.existsByIdVehicle(command.idVehicle())) {
            throw new IllegalArgumentException("This Vehicle already exists");
        }
        var visit = new Visit(command);
        try {
            this.visitRepository.save(visit);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while saving visit");
        }
        return visit.getId();
    }
}
