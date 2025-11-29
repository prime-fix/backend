package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

public record UpdateAutoRepairStatusCommand(Long repairId, String status) {}