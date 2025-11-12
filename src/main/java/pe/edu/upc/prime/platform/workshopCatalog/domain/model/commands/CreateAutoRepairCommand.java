package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

public record CreateAutoRepairCommand(String customerName, String repairDate, String repairTime, String description) {}