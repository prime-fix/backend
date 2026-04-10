package pe.edu.upc.prime.platform.autorepair.catalog.domain.services;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.*;

import java.util.Optional;

/**
 * AutoRepair interface for handling auto repair - related commands
 */
public interface AutoRepairCommandService {

    /**
     * Handle the creation of a new Auto Repair based on the provided command
     *
     * @param command the command containing the auto repair information
     * @return the ID of the newly created auto repair
     */
    Long handle(CreateAutoRepairCommand command);

    /**
     * Handles the update of an Auto Repair based on the provided command
     * @param command the command containing the updated auto repair information
     * @return an Optional containing the update AutoRepair if successful, or empty if not found
     */
    Optional<AutoRepair> handle(UpdateAutoRepairCommand command);

    /**
     * Handle the delection of an Auto Repair based on the provided command
     * @param command the command containing the ID of the AutoRepair to be related
     */
    void handle(DeleteAutoRepairCommand command);

    /**
     * Handle an add service to AutoRepair command
     * @param command The add service to AutoRepair Service catalog command containing the service id and auto repair id
     */
    void handle(AddServiceToAutoRepairServiceCatalogCommand command);

    /**
     * Increments technicians count for the target auto repair.
     *
     * @param command the increment technicians count command
     * @return current technicians count after persistence
     */
    Integer handle(IncrementAutoRepairTechniciansCountCommand command);

    /**
     * Decrements technicians count for the target auto repair.
     *
     * @param command the decrement technicians count command
     * @return current technicians count after persistence
     */
    Integer handle(DecrementAutoRepairTechniciansCountCommand command);
}
