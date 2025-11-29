package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

public record CreateLocationCommand(
        String address,
        String district,
        String department

) {}