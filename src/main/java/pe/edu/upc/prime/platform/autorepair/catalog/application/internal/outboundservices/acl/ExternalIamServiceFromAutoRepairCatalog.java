package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.interfaces.acl.IamContextFacade;

/**
 * Service to interact with the IAM context from the Auto Repair Catalog
 */
@Service
public class ExternalIamServiceFromAutoRepairCatalog {
    /**
     * IAM context facade
     */
    private final IamContextFacade iamContextFacade;

    /**
     * Constructor for ExternalIamServiceFromAutoRepairCatalog
     *
     * @param iamContextFacade the IAM context facade
     */
    public ExternalIamServiceFromAutoRepairCatalog(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Check if a user account exists by its ID
     *
     * @param userAccountId the ID of the user account to check
     * @return true if the user account exists, false otherwise
     */
    public boolean exitsUserAccountById(Long userAccountId) {
        return iamContextFacade.existsUserAccountById(userAccountId);
    }
}
