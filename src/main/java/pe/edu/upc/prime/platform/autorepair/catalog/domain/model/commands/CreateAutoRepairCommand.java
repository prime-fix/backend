package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Command to create a new auto repair.
 *
 * @param contact_email the contact email of the auto repair
 * @param ruc the RUC of the auto repair
 * @param userAccountId the user account ID associated with the auto repair
 */
public record CreateAutoRepairCommand(String contact_email, String ruc,
                                      UserAccountId userAccountId) {

    /**
     * Constructor with validation.
     *
     * @param contact_email the contact email of the auto repair
     * @param ruc the RUC of the auto repair
     * @param userAccountId the user account ID associated with the auto repair
     */
    public CreateAutoRepairCommand {
        Objects.requireNonNull(contact_email, "[CreateAutoRepairCommand] Contact email must not be null");
        Objects.requireNonNull(ruc, "[CreateAutoRepairCommand] RUC must not be null");
        Objects.requireNonNull(userAccountId, "[CreateAutoRepairCommand] User account ID must not be null");

        if (contact_email.isBlank()) {
            throw new IllegalArgumentException("[CreateAutoRepairCommand] Contact email must not be blank");
        }
        if (ruc.isBlank()) {
            throw new IllegalArgumentException("[CreateAutoRepairCommand] RUC must not be blank");
        }
        if (ruc.length() != Util.RUC_LENGTH) {
            throw new IllegalArgumentException("[CreateAutoRepairCommand] RUC must have length " + Util.RUC_LENGTH);
        }
    }
}