package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

public record LocationResponse(
        String locationId,
        String address,
        String district,
        String department
) {
}
