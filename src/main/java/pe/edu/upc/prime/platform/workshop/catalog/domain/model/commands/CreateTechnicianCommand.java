package pe.edu.upc.prime.platform.workshop.catalog.domain.model.commands;

public record CreateTechnicianCommand(String name, String specialty, Boolean available) {}