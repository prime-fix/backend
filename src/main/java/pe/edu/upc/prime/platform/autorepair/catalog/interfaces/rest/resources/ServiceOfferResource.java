package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import java.math.BigDecimal;

public record ServiceOfferResource(
        Long serviceOfferId,
        Long serviceId,
        String serviceName,
        BigDecimal price
) {
}
