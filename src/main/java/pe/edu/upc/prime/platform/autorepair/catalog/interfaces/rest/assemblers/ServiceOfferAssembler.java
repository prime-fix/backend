package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities.ServiceOffer;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceOfferResource;

/**
 * Assembler class to convert ServiceOffer entities to ServiceOfferResource
 */
public class ServiceOfferAssembler {
    /**
     * Converts a ServiceOffer entity to a ServiceOfferResource
     *
     * @param entity the ServiceOffer entity to convert
     * @return the corresponding ServiceOfferResource
     */
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
