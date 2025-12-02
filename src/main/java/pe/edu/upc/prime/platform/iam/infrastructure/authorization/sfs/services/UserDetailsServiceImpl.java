package pe.edu.upc.prime.platform.iam.infrastructure.authorization.sfs.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;

/**
 * This class is responsible for providing the user details to the Spring Security framework.
 * It implements the UserDetailsService interface.
 */
@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    /**
     * Constructor with dependencies.
     *
     * @param userAccountRepository The user account repository.
     */
    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * This method is responsible for loading the user account details from the database.
     *
     * @param username The username.
     * @return The UserDetails object.
     * @throws UsernameNotFoundException If the user account is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userAccountRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User account not found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
