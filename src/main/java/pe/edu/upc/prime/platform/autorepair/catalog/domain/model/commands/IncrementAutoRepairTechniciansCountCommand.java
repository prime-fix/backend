package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.util.Objects;

/**
 * Command to increment technicians count of an auto repair.
 *
 * @param autoRepairId the auto repair ID
 */
public record IncrementAutoRepairTechniciansCountCommand(Long autoRepairId) {

    public IncrementAutoRepairTechniciansCountCommand {
        Objects.requireNonNull(autoRepairId,
                "[IncrementAutoRepairTechniciansCountCommand] Auto repair ID must not be null");
    }
}

