package pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

/**
 * Aggregate root representing a Technician within the AutoRepair context.
 */
@Entity
@Table(name = "technicians")
public class Technician extends AuditableAbstractAggregateRoot<Technician> {


    @Getter
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Getter
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "AutoRepairId",
            column = @Column(name = "auto_repair_id", nullable = false)
    )
    @JsonProperty("id_auto_repair")
    private AutoRepairId autoRepairId;

    /**
     * Default constructor for JPA.
     */
    public Technician() {}

    /**
     * Constructs a Technician aggregate from a CreateTechnicianCommand.
     *
     * @param command createTechnicianCommand containing technician details
     */
    public Technician(CreateTechnicianCommand command) {
        this.name = command.name();
        this.lastName = command.lastName();
        this.autoRepairId = command.autoRepairId();
    }

    /**
     * Updates this Technician aggregate using data from an UpdateTechnicianCommand.
     *
     * @param command updateTechnicianCommand containing new technician data
     */
    public void updateTechnician(UpdateTechnicianCommand command) {
        this.name = command.name();
        this.lastName = command.lastName();
    }
}
