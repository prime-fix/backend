package pe.edu.upc.prime.platform.iam.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.queries.ExistsUserByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.UserQueryService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserQueryService interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    /**
     * The repository to access user data.
     */
    private final UserRepository userRepository;

    /**
     * Constructor for UserQueryServiceImpl.
     *
     * @param userRepository the repository to access user data
     */
    public UserQueryServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }

    /**
     * Handle the GetAllUsersQuery.
     *
     * @param query the query to get all users
     * @return a list of all users
     */
    @Override
    public List<User> handle(GetAllUsersQuery query) { return this.userRepository.findAll(); }

    /**
     * Handle the GetUserByIdQuery.
     *
     * @param query the query to get a user by ID
     * @return an optional user
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return this.userRepository.findById(query.idUser());
    }

    /**
     * Handle the ExistsUserByIdQuery.
     *
     * @param query the query to check if a user exists by ID
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean handle(ExistsUserByIdQuery query) { return this.userRepository.existsById(query.idUser()); }
}
