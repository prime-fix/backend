package pe.edu.upc.prime.platform.autorepair.register.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.acl.AutoRepairCatalogContextFacade;

/***
 * External Service to interact with Auto Repair Catalog from Auto Repair Register context
 */
@Service
public class ExternalAutoRepairCatalogServiceFromAutoRepairRegister {
    /**
     * Facade for Auto Repair Catalog operations
     */
    private final AutoRepairCatalogContextFacade  autoRepairCatalogContextFacade;

    /**
     * Constructor for ExternalAutoRepairCatalogServiceFromAutoRepairRegister
     *
     * @param autoRepairCatalogContextFacade the Auto Repair Catalog Context Facade
     */
    public ExternalAutoRepairCatalogServiceFromAutoRepairRegister(
            AutoRepairCatalogContextFacade autoRepairCatalogContextFacade) {
        this.autoRepairCatalogContextFacade = autoRepairCatalogContextFacade;
    }

    /**
     * Check if an auto repair exists by its ID.
     *
     * @param autoRepairId the ID of the auto repair
     * @return true if the auto repair exists, false otherwise
     */
    public boolean existsAutoRepairById(Long autoRepairId) {
        return this.autoRepairCatalogContextFacade.existsAutoRepairById(autoRepairId);
    }
}
