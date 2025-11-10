package pe.edu.upc.prime.platform.workshop.catalog.domain.model.commands;

public record CreateAutoRepairCommand(String customerName, String repairDate, String repairTime, String description) {}