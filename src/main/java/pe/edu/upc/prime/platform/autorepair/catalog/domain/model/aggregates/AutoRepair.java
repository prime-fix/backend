package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects.ServiceCatalog;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.math.BigDecimal;

/**
 * AutoRepair Aggregate Root
 */
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
    @Column(name = "ruc", nullable = false, length = Util.RUC_LENGTH)
    private String ruc;

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

    /**
     * Constructor with CreateAutoRepairCommand
     *
     * @param command the command to create the AutoRepair
     */
    public AutoRepair(CreateAutoRepairCommand command) {
        this.contact_email = command.contact_email();
        this.technicians_count = 0;
        this.ruc = command.ruc();
        this.userAccountId = command.userAccountId();
        this.serviceCatalog = new ServiceCatalog();
    }

    /**
     * Constructor with ServiceCatalog
     *
     * @param serviceCatalog ServiceCatalog
     */
    public AutoRepair(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }

    /**
     * Constructor for JPA
     */
    public AutoRepair() {
        serviceCatalog = new ServiceCatalog();
    }

    /**
     * Update AutoRepair
     *
     * @param command the command to update the AutoRepair
     */
    public void updateAutoRepair(UpdateAutoRepairCommand command) {
        this.contact_email = command.contact_email();
        this.technicians_count = command.technicians_count();
        this.ruc = command.ruc();
        this.userAccountId = command.userAccountId();
    }

    /**
     * Register New Service Offer
     * @param service the service to be offered
     * @param price the price of the service
     */
    public void registerNewOffer(Service service, BigDecimal price){
            this.serviceCatalog.addServiceOffer(this,service,price);
    }
}