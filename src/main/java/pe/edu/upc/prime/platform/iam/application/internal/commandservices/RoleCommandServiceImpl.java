package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Role;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.services.RoleCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

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
     * Handles the creation of a new role based on the provided command.
     *
     * @param command the command containing the role information
     * @return the ID of the newly created role
     */
    @Override
    public String handle(CreateRoleCommand command) {
        /*
        var roleId = command.idRole();
        var roleName = command.roleName();
        var roleDescription = command.roleDescription();

        if (this.roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("[RoleCommandServiceImpl] Role with the same id "
                    + roleId + " already exists.");
        }

        if (this.roleRepository.existsByRoleName(roleName)) {
            throw new IllegalArgumentException("[RoleCommandServiceImpl] Role with the same name " +
                    roleName.name() + " already exists.");
        }

        if (this.roleRepository.existsByRoleDescription(roleDescription)) {
            throw new IllegalArgumentException("[RoleCommandServiceImpl] Role with the same description " +
                    roleDescription.description() + " already exists.");
        }
        */
        var role = new Role(command);
        try {
            this.roleRepository.save(role);
        } catch (Exception e) {
            throw new PersistenceException("[RoleCommandServiceImpl] Error while saving Role: "
                    + e.getMessage());
        }
        return role.getId().toString();
    }

    /**
     * Handles the update of an existing role based on the provided command.
     *
     * @param command the command containing the updated role information
     * @return an Optional containing the updated role if successful
     */
    @Override
    public Optional<Role> handle(UpdateRoleCommand command) {
        var roleId = command.idRole();
        var roleName = command.roleName();
        var roleDescription = command.roleDescription();

        if (!this.roleRepository.existsById(Long.valueOf(roleId))) {
            throw new NotFoundIdException(Role.class, roleId);
        }

        if (this.roleRepository.existsByRoleNameAndIdIsNot(roleName, Long.valueOf(roleId))) {
            throw new IllegalArgumentException("[RoleCommandServiceImpl] Role with the same name " +
                    roleName.name() + " already exists.");
        }

        if (this.roleRepository.existsByRoleDescriptionAndIdIsNot(roleDescription, Long.valueOf(roleId))) {
            throw new IllegalArgumentException("[RoleCommandServiceImpl] Role with the same description " +
                    roleDescription.description() + " already exists.");
        }

        var roleToUpdate = this.roleRepository.findById(Long.valueOf(roleId)).get();
        roleToUpdate.updateRole(command);

        try {
            this.roleRepository.save(roleToUpdate);
            return Optional.of(roleToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[RoleCommandServiceImpl] Error while updating Role: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a role based on the provided command.
     *
     * @param command the command containing the ID of the role to be deleted
     */
    @Override
    public void handle(DeleteRoleCommand command) {
        if (!this.roleRepository.existsById(Long.valueOf(command.idRole()))) {
            throw new NotFoundIdException(Role.class, command.idRole());
        }

        try {
            this.roleRepository.deleteById(Long.valueOf(command.idRole()));
        } catch (Exception e) {
            throw new PersistenceException("[RoleCommandServiceImpl] Error while deleting Role: "
                    + e.getMessage());
        }
    }
}
