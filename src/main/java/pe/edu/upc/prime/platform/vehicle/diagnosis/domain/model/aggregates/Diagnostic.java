package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events.DiagnosticRegisteredEvent;

/**
 * Diagnostic Aggregate Root
 */
@Entity
@Table(name = "diagnostic")
public class Diagnostic extends AuditableAbstractAggregateRoot<Diagnostic> {

    @Getter
    @Column(name = "price", nullable = false)
    private Float price;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "vehicleId",
                    column = @Column(name = "vehicle_id", nullable = false))
    })
    private VehicleId vehicleId;

    @Getter
    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    /**
     * Default constructor for JPA
     */
    public Diagnostic() {
    }

    /**
     * Constructor for Diagnostic using CreateDiagnosticCommand
     *
     * @param command the CreateDiagnosticCommand
     */
    public Diagnostic(CreateDiagnosticCommand command) {
        this.vehicleId = command.vehicleId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
        this.registerEvent(new DiagnosticRegisteredEvent(this, command.vehicleId().value(), command.diagnosis()));
    }

    /**
     * Update Diagnostic details
     *
     * @param command the UpdateDiagnosticCommand
     */
    public void updateDiagnostic(UpdateDiagnosticCommand command) {
        this.vehicleId = command.vehicleId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
        this.registerEvent(new DiagnosticRegisteredEvent(this, command.vehicleId().value(), command.diagnosis()));
    }
}
