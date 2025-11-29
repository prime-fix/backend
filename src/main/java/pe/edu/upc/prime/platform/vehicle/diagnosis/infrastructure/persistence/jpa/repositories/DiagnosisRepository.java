package pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnostic, Long> {

    /**
     * Check if a diagnostic exists by its ID.
     *
     * @param diagnosticId the ID to check
     * @return true if a diagnostic with the given ID exists, false otherwise
     */
    boolean existsById(Long diagnosticId);

    /**
     * Finds all diagnostics associated with a given vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of diagnostics linked to the specified vehicle
     */
    List<Diagnostic> findByVehicleId(Long vehicleId);
}
