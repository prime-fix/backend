package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllUserAccountsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserAccountByUsernameQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserAccountByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling user account-related queries.
 */
public interface UserAccountQueryService {

    /**
     * Handle the query to get all user accounts.
     *
     * @param query the query to get all user accounts
     * @return a list of all user accounts
     */
    List<UserAccount> handle(GetAllUserAccountsQuery query);

    /**
     * Handle the query to get a user account by its ID.
     *
     * @param query the query containing the user account ID
     * @return an optional user account matching the ID
     */
    Optional<UserAccount> handle(GetUserAccountByIdQuery query);

    /**
     * Handle the query to get a user account by its username.
     *
     * @param query the query containing the user account username
     * @return an optional user account matching the username
     */
    Optional<UserAccount> handle(GetUserAccountByUsernameQuery query);
}
