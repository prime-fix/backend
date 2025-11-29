package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueobjects.UserAccountId;

/**
 * Command to update an existing service
 *
 * @param autoRepairId the ID of the auto repair to update
 * @param contact_email the contact email of the auto repair
 * @param technicians_count the technician count od the auto repair
 * @param RUC the RUC of the auto repair
 */
public record UpdateAutoRepairCommand(String autoRepairId, String contact_email,
                                      Integer technicians_count, String RUC, UserAccountId userAccountId) {
}
