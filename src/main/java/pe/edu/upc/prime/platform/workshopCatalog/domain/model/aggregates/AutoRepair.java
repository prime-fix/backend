package pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueObjects.UserAccountId;

@Entity
@Table(name = "auto_repairs")
public class AutoRepair extends AuditableAbstractAggregateRoot<AutoRepair> {

    @Getter
    @Column(name = "contact_email", nullable = false)
    private String contact_email;

    @Getter
    @Column(name = "technicians_count", nullable = false)
    private Integer technicians_count;

    @Getter
    @Column(name = "RUC", nullable = false)
    private String RUC;

    @Getter
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(
                    name = "user_account_id", column = @Column(name = "user_account_id", nullable = false, length = 20)
            )
    )
    private UserAccountId userAccountId;

    public AutoRepair(CreateAutoRepairCommand command) {
        this.contact_email = command.contact_email();
        this.technicians_count = command.technicians_count();
        this.RUC = command.RUC();
        this.userAccountId = command.userAccountId();
    }

    public AutoRepair() {}

    public void updateAutoRepair(UpdateAutoRepairCommand command) {
        this.contact_email = command.contact_email();
        this.technicians_count = command.technicians_count();
        this.RUC = command.RUC();
        this.userAccountId = command.userAccountId();
    }

}
