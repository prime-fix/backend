package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects.IdAutoRepair;

/**
 * Command to update an existing Technician.
 */
public record UpdateTechnicianCommand(
        String idTechnician,
        String name,
        String lastName,
        IdAutoRepair idAutoRepair
) { }
