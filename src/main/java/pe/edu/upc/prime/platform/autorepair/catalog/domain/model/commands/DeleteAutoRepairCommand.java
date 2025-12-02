package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.util.Objects;

/**
 * Command to delete an AutoRepair
 *
 * @param autoRepairId the id of the AutoRepair to delete
 */
public record DeleteAutoRepairCommand(Long autoRepairId) {

    /**
     * Constructor with validation.
     *
     * @param autoRepairId the id of the AutoRepair to delete
     */
    public DeleteAutoRepairCommand {
        if (Objects.isNull(autoRepairId) || autoRepairId <= 0) {
            throw new IllegalArgumentException("[DeleteAutoRepairCommand] AutoRepair ID must be a positive number");
        }
    }
}
