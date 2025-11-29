package pe.edu.upc.prime.platform.autorepair.catalog.domain.services;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.AddServiceToAutoRepairServiceCatalogCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateAutoRepairCommand;

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
     * Handles the update of a Auto Repair based on the provided command
     * @param command the command containing the updated auto repair information
     * @return an Optional containing the update AutoRepair if successful, or empty if not found
     */
    Optional<AutoRepair> handle(UpdateAutoRepairCommand command);

    /**
     * Handle the delection of a Auto Repair based on the provided command
     * @param command the command containing the ID of the AutoRepair to be related
     */
    void handle(DeleteAutoRepairCommand command);

    /**
     * Handle an add service to AutoRepair command
     * @param command The add service to AutoRepair Service catalog command containing the service id and auto repair id
     */
    void handle(AddServiceToAutoRepairServiceCatalogCommand command);
}
