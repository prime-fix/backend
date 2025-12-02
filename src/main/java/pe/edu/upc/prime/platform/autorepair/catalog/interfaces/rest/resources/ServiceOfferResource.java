package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Record representing a service offer resource.
 *
 * @param serviceOfferId the ID of the service offer
 * @param serviceId the ID of the service
 * @param serviceName the name of the service
 * @param price the price of the service offer
 */
public record ServiceOfferResource(
        Long serviceOfferId,
        Long serviceId,
        String serviceName,
        BigDecimal price) {
}
