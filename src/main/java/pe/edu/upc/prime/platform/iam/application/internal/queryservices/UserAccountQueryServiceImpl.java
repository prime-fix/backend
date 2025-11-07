package pe.edu.upc.prime.platform.iam.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllUserAccountsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserAccountByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountQueryService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserAccountQueryService interface.
 */
@Service
public class UserAccountQueryServiceImpl implements UserAccountQueryService {

    /**
     * The repository to access user account data.
     */
    private final UserAccountRepository userAccountRepository;

    /**
     * Constructor for UserAccountQueryServiceImpl.
     *
     * @param userAccountRepository the repository to access user account data
     */
    public UserAccountQueryServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * Get all user accounts.
     *
     * @param query the query to get all user accounts
     * @return a list of all user accounts
     */
    @Override
    public List<UserAccount> handle(GetAllUserAccountsQuery query) {
        return this.userAccountRepository.findAll();
    }

    /**
     * Get a user account by its ID.
     *
     * @param query the query containing the user account ID
     * @return an optional containing the user account if found
     */
    @Override
    public Optional<UserAccount> handle(GetUserAccountByIdQuery query) {
        return Optional.ofNullable(this.userAccountRepository.findById(query.idUserAccount())
                .orElseThrow(() -> new NotFoundIdException(UserAccount.class, query.idUserAccount())));
    }
}
