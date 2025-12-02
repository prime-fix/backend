package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.ExpectedVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllExpectedVisitsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetExpectedVisitByIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.ExpectedVisitQueryService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories.ExpectedVisitRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ExpectedVisitQueryService interface.
 */
@Service
public class ExpectedVisitQueryServiceImpl implements ExpectedVisitQueryService {
    /**
     * The repository to access expected visit data.
     */
    private final ExpectedVisitRepository expectedVisitRepository;

    /**
     * Constructor for ExpectedVisitQueryServiceImpl.
     *
     * @param expectedVisitRepository the repository to access expected visit data
     */
    public ExpectedVisitQueryServiceImpl(ExpectedVisitRepository expectedVisitRepository) {
        this.expectedVisitRepository = expectedVisitRepository;
    }

    /**
     * Handles query to get all expected visits.
     *
     * @param query the query to get all expected visits
     * @return a list of all expected visits
     */
    @Override
    public List<ExpectedVisit> handle(GetAllExpectedVisitsQuery query) {
        return this.expectedVisitRepository.findAll();
    }

    /**
     * Handles query to get an expected visit by its ID.
     *
     * @param query the query to get an expected visit by ID
     * @return an Optional containing the expected visit if found, or empty if not found
     */
    @Override
    public Optional<ExpectedVisit> handle(GetExpectedVisitByIdQuery query) {
        return Optional.ofNullable(this.expectedVisitRepository.findById(query.expectedVisitId())
                .orElseThrow(() -> new NotFoundIdException(ExpectedVisit.class, query.expectedVisitId())));
    }
}
