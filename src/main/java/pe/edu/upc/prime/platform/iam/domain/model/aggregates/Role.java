package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "roles")
public class Role extends AuditableAbstractAggregateRoot<Role> {

    @Id
    @Getter
    @Column(name = "id_role", nullable = false, unique = true)
    private String idRole;

    @Getter
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Getter
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Default constructor for JPA.
     */
    public Role() {
    }


}
