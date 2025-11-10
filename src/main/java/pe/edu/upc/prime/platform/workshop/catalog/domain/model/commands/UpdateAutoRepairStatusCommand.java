package pe.edu.upc.prime.platform.workshop.catalog.domain.model.commands;

public record UpdateAutoRepairStatusCommand(Long repairId, String status) {}