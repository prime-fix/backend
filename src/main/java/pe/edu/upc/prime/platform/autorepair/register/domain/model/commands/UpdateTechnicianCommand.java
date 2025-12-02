package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

/**
 * Command to update a technician's information.
 *
 * @param technicianId the ID of the technician to be updated
 * @param name the new name of the technician
 * @param lastName the new last name of the technician
 * @param autoRepairId the ID of the associated auto repair
 */
public record UpdateTechnicianCommand(
        Long technicianId,
        String name,
        String lastName,
        AutoRepairId autoRepairId
) { }
