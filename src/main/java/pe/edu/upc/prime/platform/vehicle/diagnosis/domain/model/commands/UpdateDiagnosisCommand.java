package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

import java.util.Objects;

public record UpdateDiagnosisCommand(Long diagnosisId, String diagnosis, Float price) {
    public UpdateDiagnosisCommand {
        Objects.requireNonNull(diagnosisId, "Diagnosis ID must not be null");
        Objects.requireNonNull(diagnosis, "Diagnosis description must not be null");
        Objects.requireNonNull(price, "Diagnosis price must not be null");
    }
}
