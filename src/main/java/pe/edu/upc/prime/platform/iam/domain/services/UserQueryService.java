package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.queries.ExistsUserByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling user-related queries.
 */
public interface UserQueryService {

    /**
     * Handle the query to get all users.
     *
     * @param query the query to get all users
     * @return a list of all users
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Handle the query to get a user by its ID.
     *
     * @param query the query containing the user ID
     * @return an optional user matching the ID
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle the query to check if a user exists by its ID.
     *
     * @param query the query containing the user ID
     * @return true if the user exists, false otherwise
     */
    boolean handle(ExistsUserByIdQuery query);
}
