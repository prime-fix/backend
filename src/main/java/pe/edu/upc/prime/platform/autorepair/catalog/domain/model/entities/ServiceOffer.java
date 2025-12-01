package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.entities.AuditableModel;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;

import java.math.BigDecimal;

/**
 * Entity representing a service offer made by an auto repair
 */
@Entity
@Table(name = "service_offers")
public class ServiceOffer extends AuditableModel {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "auto_repair_id", nullable = false)
    private AutoRepair autoRepair;

    @Getter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Getter
    @Column(name = "price", nullable = false)
    private BigDecimal price;


    /**
     * Default constructor for JPA
     */
    public ServiceOffer(){}

    /**
     * Constructor with parameters
     *
     * @param autoRepair the auto repair offering the service
     * @param service the service being offered
     * @param price the price of the service
     */
    public ServiceOffer(AutoRepair autoRepair, Service service, BigDecimal price) {
        this.autoRepair = autoRepair;
        this.service = service;
        this.price = price;
    }

    /**
     * Update the price of the service offer
     *
     * @param price the new price to set
     */
    public void updatePrice(BigDecimal price){
        this.price = price;
    }


}
