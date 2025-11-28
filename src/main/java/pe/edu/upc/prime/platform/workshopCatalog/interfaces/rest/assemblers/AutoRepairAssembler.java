package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.AutoRepairResponse;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.CreateAutoRepairRequest;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.UpdateAutoRepairRequest;

public class AutoRepairAssembler {

    public static CreateAutoRepairCommand toCommandFromRequest(CreateAutoRepairRequest request) {
        return new CreateAutoRepairCommand(
                request.contact_email(),
                request.technician_count(),
                request.RUC(),
                request.userAccountId()
        );
    }

    public static UpdateAutoRepairCommand toCommandFromRequest(String autoRepairId, UpdateAutoRepairRequest request) {
        return new UpdateAutoRepairCommand(
                autoRepairId,
                request.contact_email(),
                request.technician_count(),
                request.RUC(),
                request.userAccountId()
        );
    }

    public static AutoRepairResponse toResponseFromEntity(AutoRepair entity) {
        return new AutoRepairResponse(
                entity.getId().toString(),
                entity.getContact_email(),
                entity.getTechnicians_count().toString(),
                entity.getRUC(),
                entity.getUserAccountId().userAccountId().toString()
        );
    }


}
