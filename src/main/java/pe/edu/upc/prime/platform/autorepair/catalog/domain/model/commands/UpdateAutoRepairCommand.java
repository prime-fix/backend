package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to update an existing service
 *
 * @param autoRepairId the ID of the auto repair to update
 * @param contact_email the contact email of the auto repair
 * @param technicians_count the technician count od the auto repair
 * @param ruc the RUC of the auto repair
 */
public record UpdateAutoRepairCommand(Long autoRepairId, String contact_email,
                                      Integer technicians_count, String ruc, UserAccountId userAccountId) {

    /**
     * Constructor with validation.
     *
     * @param autoRepairId  the ID of the auto repair to update
     * @param contact_email the contact email of the auto repair
     * @param technicians_count the technician count od the auto repair
     * @param ruc the RUC of the auto repair
     * @param userAccountId the user account ID associated with the auto repair
     */
    public UpdateAutoRepairCommand {
        Objects.requireNonNull(autoRepairId, "[UpdateAutoRepairCommand] Auto repair ID must not be null");
        Objects.requireNonNull(contact_email, "[UpdateAutoRepairCommand] Contact email must not be null");
        Objects.requireNonNull(technicians_count, "[UpdateAutoRepairCommand] Technicians count must not be null");
        Objects.requireNonNull(ruc, "[UpdateAutoRepairCommand] RUC must not be null");
        Objects.requireNonNull(userAccountId, "[UpdateAutoRepairCommand] User account ID must not be null");

        if (contact_email.isBlank()) {
            throw new IllegalArgumentException("[UpdateAutoRepairCommand] Contact email must not be blank");
        }
        if (ruc.isBlank()) {
            throw new IllegalArgumentException("[UpdateAutoRepairCommand] RUC must not be blank");
        }
        if (ruc.length() != Util.RUC_LENGTH) {
            throw new IllegalArgumentException("[UpdateAutoRepairCommand] RUC must have length " + Util.RUC_LENGTH);
        }
    }
}
