package pe.edu.upc.prime.platform.payment.service.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.acl.AutoRepairCatalogContextFacade;

/**
 * Service class for interacting with external Auto Repair Catalog services via AutoRepairCatalogFacade.
 */
@Service
public class ExternalAutoRepairCatalogServiceFromPaymentService {
    /**
     * The AutoRepairCatalogFacade for Auto Repair Catalog operations.
     */
    private final AutoRepairCatalogContextFacade autoRepairCatalogContextFacade;

    /**
     * Constructor for ExternalAutoRepairCatalogServiceFromPaymentService.
     *
     * @param autoRepairCatalogContextFacade the AutoRepairCatalogFacade to be used for Auto Repair Catalog operations
     */
    public ExternalAutoRepairCatalogServiceFromPaymentService(AutoRepairCatalogContextFacade autoRepairCatalogContextFacade) {
        this.autoRepairCatalogContextFacade = autoRepairCatalogContextFacade;
    }

    /**
     * Check if an auto repair exists by its ID.
     *
     * @param autoRepairId the ID of the auto repair to check
     * @return true if the auto repair exists, false otherwise
     */
    public boolean existsAutoRepairById(Long autoRepairId) {
        return this.autoRepairCatalogContextFacade.existsAutoRepairById(autoRepairId);
    }
}
