package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.ExistsAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairQueryService;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers.AutoRepairAssembler;

import java.util.Objects;

/**
 * Facade for Auto Repair Catalog operations
 */
@Service
public class AutoRepairCatalogContextFacade {
    /**
     * Service for Auto Repair commands
     */
    private final AutoRepairQueryService autoRepairQueryService;

    /**
     * Service for Auto Repair commands
     */
    private final AutoRepairCommandService autoRepairCommandService;

    /**
     * Constructor for AutoRepairCatalogFacade
     *
     * @param autoRepairQueryService the Auto Repair Query Service
     * @param autoRepairCommandService the Auto Repair Command Service
     */
    public AutoRepairCatalogContextFacade(
                                   AutoRepairQueryService autoRepairQueryService,
                                   AutoRepairCommandService autoRepairCommandService) {
        this.autoRepairQueryService = autoRepairQueryService;
        this.autoRepairCommandService = autoRepairCommandService;
    }

    /**
     * Check if an Auto Repair exists by its ID
     *
     * @param autoRepairId the ID of the Auto Repair
     * @return true if the Auto Repair exists, false otherwise
     */
    public boolean existsAutoRepairById(Long autoRepairId) {
        var existsAutoRepairByIdQuery = new ExistsAutoRepairByIdQuery(autoRepairId);
        return this.autoRepairQueryService.handle(existsAutoRepairByIdQuery);
    }

    /**
     * Create a new Auto Repair
     contactEmail
     * @param contactEmail the contact email of the Auto Repair
     * @param ruc the RUC number of the Auto Repair
     * @param userAccountId the user account ID associated with the Auto Repair
     * @return the ID of the created Auto Repair, or 0L if creation failed
     */
    public Long createAutoRepair(String contactEmail, String ruc, Long userAccountId) {
        // Implementation goes here
        var createAutoRepairCommand = AutoRepairAssembler.toCommandFromValues(contactEmail, ruc, userAccountId);

        var autoRepairId = this.autoRepairCommandService.handle(createAutoRepairCommand);
        if (Objects.isNull(autoRepairId)) {
            return 0L;
        }
        return autoRepairId;
    }
}
