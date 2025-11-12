package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

public record AutoRepairResource(Long id, String customerName, String repairDate, String repairTime, String description, String status) {}
