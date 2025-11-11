package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands;

import java.util.Objects;

public record UpdateDiagnosisCommand(String diagnosisId, String diagnosis, Float price) {
    public UpdateDiagnosisCommand {

        if (diagnosisId.isBlank()) {
            throw new IllegalArgumentException("[UpdateDiagnosisCommand] Diagnosis ID must not be blank");
        }
        Objects.requireNonNull(diagnosis, "Diagnosis description must not be null");
        Objects.requireNonNull(price, "Diagnosis price must not be null");
    }
}
