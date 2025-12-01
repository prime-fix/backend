package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

/**
 * Command to create a technician.
 * @param name technician's name
 * @param lastName technician's last name
 * @param autoRepairId auto repair shop's id
 */
public record CreateTechnicianCommand(
        String name,
        String lastName,
        AutoRepairId autoRepairId
) {}
