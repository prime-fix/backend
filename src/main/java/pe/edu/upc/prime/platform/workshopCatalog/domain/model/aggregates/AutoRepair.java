package pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueObjects.AutoRepairStatus;
//import pe.edu.upc.prime.platform.workshop.catalog.domain.model.AuditableAbstractAggregateRoot;
import java.util.Date;

@Entity
@Table(name = "auto_repairs")
public class AutoRepair extends AuditableAbstractAggregateRoot<AutoRepair> {

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "customer_name", length = 100, nullable = false)
    private String customerName;

    @Getter
    @NotNull
    @Column(name = "repair_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date repairDate;

    @Getter
    @NotNull
    @Column(name = "repair_time", nullable = false)
    private String repairTime;

    @Getter
    @Column(name = "description", length = 500)
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "status", column = @Column(name = "status", nullable = false))
    })
    private AutoRepairStatus status;

    public AutoRepair() {}

    public AutoRepair(String customerName, Date repairDate, String repairTime, String description) {
        this.customerName = customerName;
        this.repairDate = repairDate;
        this.repairTime = repairTime;
        this.description = description;
        this.status = new AutoRepairStatus("PENDING");
    }

    public void acceptRepair() {
        this.status = new AutoRepairStatus("ACCEPTED");
    }

    public void rejectRepair() {
        this.status = new AutoRepairStatus("REJECTED");
    }

    public void completeRepair() {
        this.status = new AutoRepairStatus("COMPLETED");
    }

    public String getStatus() {
        return status.status();
    }
}
