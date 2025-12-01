package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects.UserAccountId;

public record CreateAutoRepairCommand(
        String contact_email,
        Integer technicians_count,
        String RUC,
        UserAccountId userAccountId)
{

}