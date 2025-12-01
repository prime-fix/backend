package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.AutoRepairResponse;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.CreateAutoRepairRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceOfferResource;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.UpdateAutoRepairRequest;

import java.util.List;
import java.util.stream.Collectors;

public class AutoRepairAssembler {

    public static CreateAutoRepairCommand toCommandFromRequest(CreateAutoRepairRequest request) {
        return new CreateAutoRepairCommand(
                request.contact_email(),
                request.technician_count(),
                request.ruc(),
                new UserAccountId(request.userAccountId())
        );
    }

    public static UpdateAutoRepairCommand toCommandFromRequest(Long autoRepairId, UpdateAutoRepairRequest request) {
        return new UpdateAutoRepairCommand(
                autoRepairId,
                request.contact_email(),
                request.technician_count(),
                request.RUC(),
                request.userAccountId()
        );
    }

    public static AutoRepairResponse toResponseFromEntity(AutoRepair entity) {

        List<ServiceOfferResource> serviceOffer = entity.getServiceCatalog().getServiceOffers().stream()
                .map(ServiceOfferAssembler::toResourceFromEntity)
                .collect(Collectors.toUnmodifiableList());

        return new AutoRepairResponse(
                entity.getId().toString(),
                entity.getContact_email(),
                entity.getTechnicians_count().toString(),
                entity.getRUC(),
                entity.getUserAccountId().userAccountId(),
                serviceOffer
        );
    }


}
