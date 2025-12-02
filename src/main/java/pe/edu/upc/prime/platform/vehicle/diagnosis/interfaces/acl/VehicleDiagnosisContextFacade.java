package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.ExistsExpectedVisitByIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.ExpectedVisitCommandService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.ExpectedVisitQueryService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.assemblers.ExpectedVisitAssembler;

import java.util.Objects;

/**
 * Facade for Vehicle Diagnosis context operations, providing methods to manage expected visits.
 */
@Service
public class VehicleDiagnosisContextFacade {
    /**
     * The expected visit command service for handling expected visit-related commands.
     */
    private final ExpectedVisitCommandService expectedVisitCommandService;
    /**
     * The expected visit query service for handling expected visit-related queries.
     */
    private final ExpectedVisitQueryService expectedVisitQueryService;

    /**
     * Constructs a VehicleDiagnosisContextFacade with the specified command and query services.
     *
     * @param expectedVisitCommandService the service for handling expected visit commands
     * @param expectedVisitQueryService   the service for handling expected visit queries
     */
    public VehicleDiagnosisContextFacade(ExpectedVisitCommandService expectedVisitCommandService,
                                         ExpectedVisitQueryService expectedVisitQueryService) {
        this.expectedVisitCommandService = expectedVisitCommandService;
        this.expectedVisitQueryService = expectedVisitQueryService;
    }

    /**
     * Check if an expected visit exists by its ID.
     *
     * @param expectedVisitId the ID of the expected visit to check
     * @return true if the expected visit exists, false otherwise
     */
    public boolean existsExpectedVisitById(Long expectedVisitId) {
        var existsExpectedVisitByIdQuery = new ExistsExpectedVisitByIdQuery(expectedVisitId);
        return expectedVisitQueryService.handle(existsExpectedVisitByIdQuery);
    }

    /**
     * Create a new expected visit for a given visit ID.
     *
     * @param visitId the ID of the visit to create an expected visit for
     * @return the ID of the created expected visit, or 0L if creation failed
     */
    public Long createExpectedVisit(Long visitId, Long vehicleId) {
        var createExpectedVisitCommand = ExpectedVisitAssembler.toCommandFromValues(visitId, vehicleId);

        var expectedVisitId = this.expectedVisitCommandService.handle(createExpectedVisitCommand);
        if (Objects.isNull(expectedVisitId)) {
            return 0L;
        }
        return expectedVisitId;
    }
}
