package pe.edu.upc.prime.platform.workshop.catalog.domain.model.valueObjects;

public record TechnicianSpecialty(String specialty) {
    public TechnicianSpecialty {
        if (specialty == null || specialty.isBlank()) {
            throw new IllegalArgumentException("Specialty cannot be blank");
        }
    }
}