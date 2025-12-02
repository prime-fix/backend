package pe.edu.upc.prime.platform.payment.service.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.interfaces.acl.IamContextFacade;

/**
 * Service class for interacting with external IAM services via IamContextFacade.
 */
@Service
public class ExternalIamServiceFromPaymentService {
    /**
     * The IamContextFacade for IAM operations.
     */
    private final IamContextFacade iamContextFacade;

    /**
     * Constructor for ExternalIamService.
     *
     * @param iamContextFacade the IamContextFacade to be used for IAM operations
     */
    public ExternalIamServiceFromPaymentService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Check if a user account exists by its ID.
     *
     * @param userAccountId the ID of the user account to check
     * @return true if the user account exists, false otherwise
     */
    public boolean existsUserAccountById(Long userAccountId) {
        return this.iamContextFacade.existsUserAccountById(userAccountId);
    }
}
