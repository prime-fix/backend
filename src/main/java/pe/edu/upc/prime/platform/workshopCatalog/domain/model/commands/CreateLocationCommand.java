package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

public record CreateLocationCommand(
        String address,
        String district,
        String department

) {}