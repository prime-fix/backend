package pe.edu.upc.prime.platform.iam.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.prime.platform.shared.domain.model.entities.AuditableModel;

/**
 * Role entity representing a user role in the system.
 */
@Getter
@Entity
@Table(name = "roles")
public class Role extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private Roles name;

    /**
     * Default constructor for JPA.
     */
    public Role() {
    }

    /**
     * Default constructor for Role.
     *
     * @param name the role name
     */
    public Role(Roles name) {
        this.name = name;
    }



    /**
     * Gets the role name as a string.
     *
     * @return the role name
     */
    public String getStringName() {
        return name.name();
    }

    /**
     * Method to convert a role name string to a Role entity.
     * @param name the role name string
     * @return Role entity
     */
    public static Role toRoleFromName(String name) {
        return new Role(Roles.valueOf(name));
    }

}
