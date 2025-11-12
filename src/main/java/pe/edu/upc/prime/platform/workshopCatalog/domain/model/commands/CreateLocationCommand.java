package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

public record CreateLocationCommand(String name, String address, String phone, String openingHours) {}