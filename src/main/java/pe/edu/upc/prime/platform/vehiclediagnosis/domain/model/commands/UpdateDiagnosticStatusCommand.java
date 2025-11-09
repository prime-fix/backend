package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands;

import java.util.Objects;

public record UpdateDiagnosticStatusCommand(String idExpected, String newState) {
    public UpdateDiagnosticStatusCommand {
        /*if (idExpected == null || idExpected.isBlank()) {
            throw new IllegalArgumentException("idExpected cannot be null or blank");
        }
        if (newState == null || newState.isBlank()) {
            throw new IllegalArgumentException("newState cannot be null or blank");
        }*/

        Objects.requireNonNull(idExpected, "The Expected ID must not be null");
        Objects.requireNonNull(newState, "The new state must not be null");
    }
}
