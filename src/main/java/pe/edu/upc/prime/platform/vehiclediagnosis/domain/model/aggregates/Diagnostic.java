package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects.VehicleId;

@Entity
@Table(name = "diagnostic")
public class Diagnostic extends AuditableAbstractAggregateRoot<Diagnostic> {

    @Id
    @Getter
    @Column(name = "id_diagnostic", nullable = false, unique = true)
    private String idDiagnostic;

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

    public Diagnostic(CreateDiagnosticCommand command) {
        this.vehicleId = command.vehicleId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
    }
    /*@Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "expectedVisit",
                    column = @Column(name = "expected_visit", nullable = false))
    })
    private ExpectedVisit expectedVisit;*/
}
