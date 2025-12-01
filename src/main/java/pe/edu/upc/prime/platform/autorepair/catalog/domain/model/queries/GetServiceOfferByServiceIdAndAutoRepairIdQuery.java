package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries;

/**
 * Query to get a ServiceOffer by Service ID and AutoRepair ID
 *
 * @param serviceId the ID of the Service
 * @param autoRepairId the ID of the AutoRepair
 */
public record GetServiceOfferByServiceIdAndAutoRepairIdQuery(Long serviceId, Long autoRepairId) {
}
