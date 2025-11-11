package pe.edu.upc.prime.platform.data.collection.domain.model.aggregates;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name ="services")
public class Service extends AuditableAbstractAggregateRoot<Service> {

    @Id
    @Getter
    @Column(name="service_id", nullable = false, unique = true)
    @JsonProperty("service_id")
    private String serviceId;


    @Getter
    @Column(name="name", nullable = false)
    private String name;

    @Getter
    @Column(name = "description", nullable = false)
    private  String description;


    public Service(CreateServiceCommand command) {
        this.serviceId = command.serviceId();
        this.name = command.name();
        this.description = command.description();
    }

    public void updateService(UpdateServiceCommand command) {
        this.name = command.name();
        this.description = command.description();
    }

    public Service() {}
}
