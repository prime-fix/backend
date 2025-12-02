package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


/**
 * Represents one service in the system
 */
@Entity
@Table(name ="services")
public class Service extends AuditableAbstractAggregateRoot<Service> {


    @Getter
    @Column(name="name", nullable = false)
    private String name;

    @Getter
    @Column(name = "description", nullable = false)
    private  String description;

    /**
     * Constructs a Service instance from a CreateProfileCommand.
     * @param command createServiceCommand containing service details
     */
    public Service(CreateServiceCommand command) {
        this.name = command.name();
        this.description = command.description();
    }

    /**
     * Update the service with the specified details
     * @param command the UpdateServiceCommand containing the new profile details
     */
    public void updateService(UpdateServiceCommand command) {
        this.name = command.name();
        this.description = command.description();
    }

    /**
     * Default constructor for JPA
     */
    public Service() {}
}
