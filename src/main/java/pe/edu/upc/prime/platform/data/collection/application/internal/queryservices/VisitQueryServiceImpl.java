package pe.edu.upc.prime.platform.data.collection.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.*;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitQueryService;
import pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories.VisitRepository;

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
        return this.visitRepository.findById(Long.valueOf(query.visitId()));
    }

    @Override
    public List<Visit> handle(GetVisitByVehicleIdQuery query) {
        return this.visitRepository.findByVehicleId(query.vehicleId());
    }

    @Override
    public List<Visit> handle(GetVisitByAutoRepairIdQuery query) {
        return this.visitRepository.findByAutoRepairId(query.autoRepairId());
    }


}
