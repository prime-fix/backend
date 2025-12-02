package pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;

import java.util.List;

/**
 * Repository interface for managing Diagnostic entities.
 */
public interface DiagnosticRepository extends JpaRepository<Diagnostic, Long> {
    /**
     * Finds all diagnostics associated with a given vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of diagnostics linked to the specified vehicle
     */
    List<Diagnostic> findByVehicleId(VehicleId vehicleId);
}
