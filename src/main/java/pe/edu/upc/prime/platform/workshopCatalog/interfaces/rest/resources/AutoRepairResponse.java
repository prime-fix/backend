package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

public record AutoRepairResponse(
        String autoRepairId,
        String contact_email,
        String technician_count,
        String RUC,
        String userAccountId

) {
}
