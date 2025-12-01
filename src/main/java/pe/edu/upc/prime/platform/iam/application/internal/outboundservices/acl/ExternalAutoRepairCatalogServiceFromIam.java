package pe.edu.upc.prime.platform.iam.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.acl.AutoRepairCatalogContextFacade;

/**
 * Service to interact with the Auto Repair Catalog from the IAM context.
 */
@Service
public class ExternalAutoRepairCatalogServiceFromIam {
    /**
     * Context Facade for Auto Repair Catalog operations
     */
    private final AutoRepairCatalogContextFacade autoRepairCatalogContextFacade;

    /**
     * Constructor for ExternalAutoRepairCatalogServiceFromIam.
     *
     * @param autoRepairCatalogContextFacade the Auto Repair Catalog Context Facade
     */
    public ExternalAutoRepairCatalogServiceFromIam(AutoRepairCatalogContextFacade autoRepairCatalogContextFacade) {
        this.autoRepairCatalogContextFacade = autoRepairCatalogContextFacade;
    }

    /**
     * Check if an Auto Repair exists by its ID
     *
     * @param autoRepairId the ID of the Auto Repair
     * @return true if the Auto Repair exists, false otherwise
     */
    public boolean existsAutoRepairById(Long autoRepairId) {
        return autoRepairCatalogContextFacade.existsAutoRepairById(autoRepairId);
    }

    /**
     * Create a new Auto Repair
     *
     * @param contact_email the contact email of the Auto Repair
     * @param ruc the RUC number of the Auto Repair
     * @param userAccountId the user account ID associated with the Auto Repair
     * @return the ID of the created Auto Repair
     */
    public Long createAutoRepair(String contact_email, String ruc, Long userAccountId) {
        return autoRepairCatalogContextFacade.createAutoRepair(contact_email, ruc, userAccountId);
    }
}
