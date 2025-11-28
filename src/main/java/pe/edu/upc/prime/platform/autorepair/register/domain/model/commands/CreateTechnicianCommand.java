package pe.edu.upc.prime.platform.autorepair.register.domain.model.commands;



import pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects.IdAutoRepair;


public record CreateTechnicianCommand(
        String name,
        String lastName,
        IdAutoRepair idAutoRepair
) {}
