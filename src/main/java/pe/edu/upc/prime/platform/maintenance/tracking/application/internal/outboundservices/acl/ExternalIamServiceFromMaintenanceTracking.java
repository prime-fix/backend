package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.prime.platform.iam.interfaces.acl.IamContextFacade;

/**
 * Service class for interacting with external IAM services via IamContextFacade.
 */
@Service
public class ExternalIamServiceFromMaintenanceTracking {
    /**
     * The IamContextFacade for IAM operations.
     */
    private final IamContextFacade iamContextFacade;

    /**
     * Constructor for ExternalIamService.
     *
     * @param iamContextFacade the IamContextFacade to be used for IAM operations
     */
    public ExternalIamServiceFromMaintenanceTracking(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Check if a user exists by their ID.
     *
     * @param userId the ID of the user to check
     * @return true if the user exists, false otherwise
     */
    public boolean existsUserById(Long userId) {
        return this.iamContextFacade.existsUserById(userId);
    }

    /**
     * Retrieve the role associated with a given user ID.
     *
     * @param userId the ID of the user
     * @return the role associated with the user
     */
    public Roles getRoleIdByUserId(Long userId) {
        return this.iamContextFacade.getRoleByUserId(userId);
    }

}
