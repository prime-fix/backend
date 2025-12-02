package pe.edu.upc.prime.platform.data.collection.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.*;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitQueryService;
import pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories.VisitRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of VisitQueryService.
 */
@Service
public class VisitQueryServiceImpl implements VisitQueryService {
    /**
     * The Visit repository.
     */
    private final VisitRepository visitRepository;

    /**
     * Constructor for VisitQueryServiceImpl.
     * @param visitRepository the visit repository
     */
    public VisitQueryServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * Handle the query to get all visits.
     *
     * @param query the query to get all visits
     * @return a list of all visits
     */
    @Override
    public List<Visit> handle(GetAllVisitsQuery query) {
        return this.visitRepository.findAll();
    }

    /**
     * Handle the query to get a visit by its ID.
     *
     * @param query the query containing the visit ID
     * @return an optional visit matching the ID
     */
    @Override
    public Optional<Visit> handle(GetVisitByIdQuery query) {
        return this.visitRepository.findById(query.visitId());
    }

    /**
     * Handle the query to get visits by vehicle ID.
     *
     * @param query the query containing the vehicle ID
     * @return a list of visits matching the vehicle ID
     */
    @Override
    public List<Visit> handle(GetVisitByVehicleIdQuery query) {
        return this.visitRepository.findByVehicleId_VehicleId(query.vehicleId());
    }

    /**
     * Handle the query to get visits by auto repair ID.
     *
     * @param query the query containing the auto repair ID
     * @return a list of visits matching the auto repair ID
     */
    @Override
    public List<Visit> handle(GetVisitByAutoRepairIdQuery query) {
        return this.visitRepository.findByAutoRepairId_AutoRepairId(query.autoRepairId());
    }

    /**
     * Handle the query to check if a visit exists by its ID.
     *
     * @param query the query containing the visit ID
     * @return true if the visit exists, false otherwise
     */
    @Override
    public boolean handle(ExistsVisitByIdQuery query) {
        return this.visitRepository.existsById(query.visitId());
    }
}
