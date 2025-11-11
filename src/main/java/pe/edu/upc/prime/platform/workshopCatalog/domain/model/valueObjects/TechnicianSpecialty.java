package pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueObjects;

public record TechnicianSpecialty(String specialty) {
    public TechnicianSpecialty {
        if (specialty == null || specialty.isBlank()) {
            throw new IllegalArgumentException("Specialty cannot be blank");
        }
    }
}