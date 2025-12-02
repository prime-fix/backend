package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.StateVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VisitId;

import java.util.Objects;

/**
 * Command to create an Expected Visit
 *
 * @param visitId the identifier of the visit
 */
public record CreateExpectedVisitCommand(VisitId visitId, VehicleId vehicleId) {
    public CreateExpectedVisitCommand {
        Objects.requireNonNull(visitId, "[CreateExpectedVisitCommand] visitId must not be null");
        Objects.requireNonNull(vehicleId, "[CreateExpectedVisitCommand] vehicleId must not be null");
    }
}
