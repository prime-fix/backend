package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.AutoRepairResponse;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.CreateAutoRepairRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceOfferResource;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.UpdateAutoRepairRequest;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Assembler for Auto Repair related conversions
 */
public class AutoRepairAssembler {
    /**
     * Convert CreateAutoRepairRequest to CreateAutoRepairCommand
     *
     * @param request the create auto repair request
     * @return the corresponding to create auto repair command
     */
    public static CreateAutoRepairCommand toCommandFromRequest(CreateAutoRepairRequest request) {
        return new CreateAutoRepairCommand(
                request.contactEmail(),
                request.ruc(),
                new UserAccountId(request.userAccountId())
        );
    }

    /**
     * Convert UpdateAutoRepairRequest to UpdateAutoRepairCommand
     *
     * @param autoRepairId the ID of the auto repair to be updated
     * @param request the update auto repair request
     * @return the corresponding to update auto repair command
     */
    public static UpdateAutoRepairCommand toCommandFromRequest(Long autoRepairId, UpdateAutoRepairRequest request) {
        return new UpdateAutoRepairCommand(
                autoRepairId,
                request.contactEmail(),
                request.ruc(),
                new UserAccountId(request.userAccountId())
        );
    }

    /**
     * Convert AutoRepair entity to AutoRepairResponse
     *
     * @param entity the auto repair entity
     * @return the corresponding auto repair response
     */
    public static AutoRepairResponse toResponseFromEntity(AutoRepair entity) {

        List<ServiceOfferResource> serviceOffer = entity.getServiceCatalog().getServiceOffers().stream()
                .map(ServiceOfferAssembler::toResourceFromEntity)
                .collect(Collectors.toUnmodifiableList());

        return new AutoRepairResponse(
                entity.getId(),
                entity.getContact_email(),
                entity.getTechniciansCount(),
                entity.getRuc(),
                entity.getUserAccountId().value(),
                serviceOffer
        );
    }

    /**
     * Convert from raw values to CreateAutoRepairCommand
     *
     * @param contactEmail the contact email of the auto repair
     * @param ruc the RUC of the auto repair
     * @param userAccountId the user account ID associated with the auto repair
     * @return the corresponding CreateAutoRepairCommand
     */
    public static CreateAutoRepairCommand toCommandFromValues(String contactEmail, String ruc, Long userAccountId) {
        return new CreateAutoRepairCommand(
                contactEmail,
                ruc,
                new UserAccountId(userAccountId)
        );
    }

}
