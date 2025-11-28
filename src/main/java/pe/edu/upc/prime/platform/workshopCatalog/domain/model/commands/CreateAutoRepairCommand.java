package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueObjects.UserAccountId;

public record CreateAutoRepairCommand(
        String contact_email,
        Integer technicians_count,
        String RUC,
        UserAccountId userAccountId)
{

}