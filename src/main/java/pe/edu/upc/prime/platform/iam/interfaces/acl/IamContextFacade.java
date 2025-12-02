package pe.edu.upc.prime.platform.iam.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.queries.*;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountQueryService;
import pe.edu.upc.prime.platform.iam.domain.services.UserQueryService;

/**
 * Facade for IAM context operations, providing methods to check existence of user accounts and users.
 */
@Service
public class IamContextFacade {
    /**
     * The user account query service for handling user account-related queries.
     */
    private final UserAccountQueryService userAccountQueryService;

    /**
     * The user query service for handling user-related queries.
     */
    private final UserQueryService userQueryService;

    /**
     * Constructs an IamContextFacade with the specified command and query services.
     *
     * @param userAccountQueryService the service for handling user account queries
     * @param userQueryService the service for handling user queries
     */
    public IamContextFacade(UserAccountQueryService userAccountQueryService,
                            UserQueryService userQueryService) {
        this.userAccountQueryService = userAccountQueryService;
        this.userQueryService = userQueryService;
    }

    /**
     * Checks if a user account exists by its ID.
     *
     * @param userAccountId the ID of the user account to check
     * @return true if the user account exists, false otherwise
     */
    public boolean existsUserAccountById(Long userAccountId) {
        var existsUserAccountByIdQuery = new ExistsUserAccountByIdQuery(userAccountId);
        return userAccountQueryService.handle(existsUserAccountByIdQuery);
    }

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user to check
     * @return true if the user exists, false otherwise
     */
    public boolean existsUserById(Long userId) {
        var existsUserByIdQuery = new ExistsUserByIdQuery(userId);
        return userQueryService.handle(existsUserByIdQuery);
    }

    /**
     * Retrieves the role ID associated with a given user ID.
     *
     * @param userId the ID of the user
     * @return the role ID associated with the user, or 0L if the user account does not exist
     */
    public Roles getRoleByUserId(Long userId) {
        var getUserAccountByUserId = new GetUserAccountByUserIdQuery(userId);
        var optionalUserAccount = userAccountQueryService.handle(getUserAccountByUserId);

        return optionalUserAccount.map(userAccount -> userAccount.getRole().getName()).orElse(null);
    }
}
