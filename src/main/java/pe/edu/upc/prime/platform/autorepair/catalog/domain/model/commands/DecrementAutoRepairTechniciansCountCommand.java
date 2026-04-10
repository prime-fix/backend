package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.util.Objects;

/**
 * Command to decrement technicians count of an auto repair.
 *
 * @param autoRepairId the auto repair ID
 */
public record DecrementAutoRepairTechniciansCountCommand(Long autoRepairId) {

    public DecrementAutoRepairTechniciansCountCommand {
        Objects.requireNonNull(autoRepairId,
                "[DecrementAutoRepairTechniciansCountCommand] Auto repair ID must not be null");
    }
}

