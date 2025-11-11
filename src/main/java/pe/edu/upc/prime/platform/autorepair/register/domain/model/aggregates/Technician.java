package pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Aggregate root representing a Technician within the AutoRepair context.
 */
@Entity
@Table(name = "technicians")
public class Technician extends AuditableAbstractAggregateRoot<Technician> {

    @Id
    @Getter
    @Column(name = "id_technician", nullable = false, unique = true)
    @JsonProperty("id_technician")
    private String idTechnician;

    @Getter
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Getter
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "idAutoRepair",
            column = @Column(name = "id_auto_repair", nullable = false)
    )
    @JsonProperty("id_auto_repair")
    private IdAutoRepair idAutoRepair;

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
        this.idTechnician = command.idTechnician();
        this.name = command.name();
        this.lastName = command.lastName();
        this.idAutoRepair = command.idAutoRepair();
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

    /**
     * Returns the raw IdAutoRepair value as a Long.
     *
     * @return the numeric value of IdAutoRepair
     */
    public Long getIdAutoRepairValue() {
        return idAutoRepair != null ? idAutoRepair.getIdAutoRepair() : null;
    }
}
