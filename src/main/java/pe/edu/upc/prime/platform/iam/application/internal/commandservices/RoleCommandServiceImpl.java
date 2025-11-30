package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.commands.SeedRolesCommand;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.prime.platform.iam.domain.services.RoleCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

import java.util.Arrays;

/**
 * Implementation of the RoleCommandService interface.
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    /**
     * The repository to access role data.
     */
    private final RoleRepository roleRepository;

    /**
     * Constructor for RoleCommandServiceImpl.
     *
     * @param roleRepository the repository to access role data
     */
    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /**
     * This method will handle the {@link SeedRolesCommand} and will create the roles if not exists.
     *
     * @param command {@link SeedRolesCommand}
     * @see SeedRolesCommand
     */
    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values())
                .forEach(role -> {
                    if (!roleRepository.existsByName(role)) {
                        roleRepository.save(new Role(Roles.valueOf(role.name())));
                    }
                });
    }
}
