package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.interfaces.acl.IamContextFacade;

/**
 * Service class for interacting with external IAM services via IamContextFacade.
 */
@Service
public class ExternalIamService {
    /**
     * The IamContextFacade for IAM operations.
     */
    private final IamContextFacade iamContextFacade;

    /**
     * Constructor for ExternalIamService.
     *
     * @param iamContextFacade the IamContextFacade to be used for IAM operations
     */
    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    public boolean existsUserById(Long userId) {
        return this.iamContextFacade.existsUserById(userId);
    }
}
