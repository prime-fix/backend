package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VisitId;


@Entity
@Table(name = "expected_visit")
public class ExpectedVisit extends AuditableAbstractAggregateRoot<ExpectedVisit> {


    /*
    * Represents the date of the expected visit.
    * @param dateExpectedVisit the date of the expected visit
    */
    @Getter
    @Column(name = "state_visit", nullable = false)
    private String stateVisit;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "visitId",
                    column = @Column(name = "id_visit", nullable = false))
    })
    private VisitId visitId;

    @Getter
    @Column(name = "id_scheduled", length = 2, nullable = false)
    private Boolean idScheduled;

    /*
     * Default constructor for JPA.
     */
    public ExpectedVisit() {  }

    public ExpectedVisit(String idExpected, String stateVisit, VisitId visitId, Boolean isScheduled) {
        this.stateVisit = stateVisit;
        this.visitId = visitId;
        this.idScheduled = isScheduled;
    }

    public boolean makeAScheduled() {
        if (!this.idScheduled) {
            this.idScheduled = true;
            this.stateVisit = "SCHEDULED";
            return true;
        }
        return false;
    }
}
