package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects.ServiceCatalog;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects.UserAccountId;

import java.math.BigDecimal;

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
                    name = "user_account_Id", column = @Column(name = "user_account_id", nullable = false)
            )
    )
    private UserAccountId userAccountId;

    @Getter
    @Embedded
    private final ServiceCatalog serviceCatalog;

    public AutoRepair(CreateAutoRepairCommand command) {
        this.contact_email = command.contact_email();
        this.technicians_count = command.technicians_count();
        this.RUC = command.RUC();
        this.userAccountId = command.userAccountId();
        this.serviceCatalog = new ServiceCatalog();
    }

    public AutoRepair(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }


    public AutoRepair() {
        serviceCatalog = new ServiceCatalog();
    }

    public void updateAutoRepair(UpdateAutoRepairCommand command) {
        this.contact_email = command.contact_email();
        this.technicians_count = command.technicians_count();
        this.RUC = command.RUC();
        this.userAccountId = command.userAccountId();
    }

    public void registerNewOffer(Service service, BigDecimal price){
            this.serviceCatalog.addServiceOffer(this,service,price);
    }
}