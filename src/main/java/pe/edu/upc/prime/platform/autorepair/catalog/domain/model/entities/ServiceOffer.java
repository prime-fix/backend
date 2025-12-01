package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.entities.AuditableModel;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;

import java.math.BigDecimal;

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


    public ServiceOffer(){}

    public ServiceOffer(AutoRepair autoRepair, Service service, BigDecimal price) {
        this.autoRepair = autoRepair;
        this.service = service;
        this.price = price;
    }

    public void updatePrice(BigDecimal price){
        this.price = price;
    }


}
