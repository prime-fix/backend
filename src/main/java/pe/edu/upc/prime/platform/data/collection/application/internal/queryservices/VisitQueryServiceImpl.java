package pe.edu.upc.center.data_collection.data.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetAllVisitsQuery;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdAutoRepair;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdQuery;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdVehicle;
import pe.edu.upc.center.data_collection.data.domain.services.VisitQueryService;
import pe.edu.upc.center.data_collection.data.infrastructure.persistance.jpa.repositories.VisitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VisitQueryServiceImpl implements VisitQueryService {

    private final VisitRepository visitRepository;

    public VisitQueryServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> handle(GetAllVisitsQuery query) {
        return this.visitRepository.findAll();
    }

    @Override
    public Optional<Visit> handle(GetVisitByIdQuery query) {
        return this.visitRepository.findById(query.idVisit());
    }

    @Override
    public Optional<Visit> handle(GetVisitByIdAutoRepair query) {
        return this.visitRepository.findByIdAutoRepair(query.idAutoRepair());
    }

    @Override
    public Optional<Visit> handle(GetVisitByIdVehicle query) {
        return this.visitRepository.findByIdVehicle(query.idVehicle());
    }
}
