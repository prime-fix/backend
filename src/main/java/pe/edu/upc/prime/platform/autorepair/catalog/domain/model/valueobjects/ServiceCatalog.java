package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities.ServiceOffer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Embeddable
public class ServiceCatalog {

    @Getter
    @OneToMany(mappedBy = "autoRepair", cascade = CascadeType.ALL)
    private List<ServiceOffer> serviceOffers;

    public ServiceCatalog(){
        this.serviceOffers = new ArrayList<>();
    }

    public void addServiceOffer(AutoRepair autoRepair, Service service, BigDecimal price){

        ServiceOffer serviceOffer = new ServiceOffer(autoRepair, service, price);
        ServiceOffer originalService = null;
        this.serviceOffers.add(serviceOffer);
    }

    public List<ServiceOffer> getServiceOffers() {
        return List.copyOf(serviceOffers);
    }

    public ServiceOffer getOfferByServiceId(Long serviceId) {
        return this.serviceOffers.stream()
                .filter(offer -> offer.getService().getId().equals(serviceId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "The service ID dont exist")
                );
    }

    public ServiceOffer getOfferByAutoRepairId(Long autoRepairId) {
        return this.serviceOffers.stream()
                .filter(offer -> offer.getAutoRepair().getId().equals(autoRepairId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "The auto Repair dont exist")
                );
    }

    public boolean isEmpty(){
        return this.serviceOffers.isEmpty();
    }

}
