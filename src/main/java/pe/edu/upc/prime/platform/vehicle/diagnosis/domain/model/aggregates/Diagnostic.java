package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosticCommand;

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
            @AttributeOverride(name = "idVehicle",
                    column = @Column(name = "id_vehicle", nullable = false))
    })
    private VehicleId vehicleId;

    @Getter
    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expected_visit_id")
    private ExpectedVisit expectedVisit;

    /**
     * Default constructor for JPA
     */
    public Diagnostic() {
    }

    /**
     * Constructor for Diagnostic using CreateDiagnosticCommand
     *
     * @param command the CreateDiagnosticCommand
     * @param expectedVisit the ExpectedVisit associated with the Diagnostic
     */
    public Diagnostic(CreateDiagnosticCommand command, ExpectedVisit expectedVisit) {
        this.vehicleId = command.vehicleId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
        this.expectedVisit = expectedVisit;
    }

    /**
     * Update Diagnostic details
     *
     * @param command the UpdateDiagnosticCommand
     * @param expectedVisit the ExpectedVisit associated with the Diagnostic
     */
    public void updateDiagnostic(UpdateDiagnosticCommand command, ExpectedVisit expectedVisit) {
        this.vehicleId = command.vehicleId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
        this.expectedVisit = expectedVisit;
    }
}
