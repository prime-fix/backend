package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

public record CreateTechnicianCommand(String name, String specialty, Boolean available) {}