package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;


import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

/**
 * Command to update an existing Technician.
 */
public record UpdateTechnicianCommand(
        Long idTechnician,
        String name,
        String lastName,
        AutoRepairId autoRepairId
) { }
