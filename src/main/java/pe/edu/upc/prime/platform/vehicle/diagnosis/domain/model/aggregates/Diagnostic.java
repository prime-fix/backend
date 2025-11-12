package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VehicleId;

@Entity
@Table(name = "diagnostic")
public class Diagnostic extends AuditableAbstractAggregateRoot<Diagnostic> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Column(name = "id_diagnostic", nullable = false, unique = true)
    private String idDiagnostic;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expected_visit_id", referencedColumnName = "id_expected")
    private ExpectedVisit expectedVisit;

    public Diagnostic(CreateDiagnosisCommand command) {
        this.vehicleId = command.vehicleId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
    }

    public Diagnostic() {
    }

    public void updateDiagnostic(UpdateDiagnosisCommand command) {
        this.idDiagnostic = command.diagnosisId();
        this.diagnosis = command.diagnosis();
        this.price = command.price();
    }

    /*public static Diagnostic sendNewDiagnostic(VehicleId vehicleId, ExpectedVisit expectedVisit) {
        String diagnosticId = "DIAG_" + System.currentTimeMillis();
        return new Diagnostic(new CreateVehicleDiagnosisCommand(
                        diagnosticId, vehicleId, "INITIAL_DIAGNOSIS", 0.0f, expectedVisit
                ));
    }*/
}
