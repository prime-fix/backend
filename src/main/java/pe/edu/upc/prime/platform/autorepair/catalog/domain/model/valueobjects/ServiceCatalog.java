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

/**
 * ServiceCatalog value object that contains a list of ServiceOffer entities.
 */
@Embeddable
public class ServiceCatalog {

    /**
     * List of service offers in the catalog.
     */
    @Getter
    @OneToMany(mappedBy = "autoRepair", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOffer> serviceOffers;

    /**
     * Constructor for ServiceCatalog.
     */
    public ServiceCatalog() {
        this.serviceOffers = new ArrayList<>();
    }

    /**
     * Adds a new service offer to the catalog.
     *
     * @param autoRepair the auto repair offering the service
     * @param service    the service being offered
     * @param price      the price of the service
     */
    public void addServiceOffer(AutoRepair autoRepair, Service service, BigDecimal price, Boolean is_active, int duration_hour) {

        ServiceOffer serviceOffer = new ServiceOffer(autoRepair, service, price, is_active, duration_hour);
        ServiceOffer originalService = null;
        this.serviceOffers.add(serviceOffer);
    }

    /**
     * Removes a service offer from the Service Catalog
     * @param autoRepair the auto repair
     * @param service the service to be removed
     */
    public void removeServiceOffer(AutoRepair autoRepair, Service service){
        boolean removed = this.serviceOffers.removeIf(serviceOffer -> serviceOffer.getAutoRepair().equals(autoRepair)
                && serviceOffer.getService().equals(service));

        if(!removed){
            throw new NoSuchElementException("Service Offer not found");
        }
    }


    /**
     * Gets an unmodifiable list of service offers.
     *
     * @return list of service offers
     */
    public List<ServiceOffer> getServiceOffers() {
        return List.copyOf(serviceOffers);
    }

    /**
     * Gets a service offer by service ID.
     *
     * @param serviceId the ID of the service
     * @return the service offer
     */
    public ServiceOffer getOfferByServiceId(Long serviceId) {
        return this.serviceOffers.stream()
                .filter(offer -> offer.getService().getId().equals(serviceId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "The service ID dont exist")
                );
    }

    /**
     * Gets a service offer by auto repair ID.
     *
     * @param autoRepairId the ID of the auto repair
     * @return the service offer
     */
    public ServiceOffer getOfferByAutoRepairId(Long autoRepairId) {
        return this.serviceOffers.stream()
                .filter(offer -> offer.getAutoRepair().getId().equals(autoRepairId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "The auto Repair dont exist")
                );
    }

    /**
     * Checks if the service catalog is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return this.serviceOffers.isEmpty();
    }

}
