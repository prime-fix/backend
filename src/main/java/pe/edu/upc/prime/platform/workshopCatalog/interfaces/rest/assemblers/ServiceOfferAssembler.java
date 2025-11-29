package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.entites.ServiceOffer;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.ServiceOfferResource;

public class ServiceOfferAssembler {

    public static ServiceOfferResource toResourceFromEntity(ServiceOffer entity) {
        Service serviceReference = entity.getService();

        return new ServiceOfferResource(
                entity.getId(),
                serviceReference.getId(),
                serviceReference.getName(),
                entity.getPrice()
        );
    }
}
