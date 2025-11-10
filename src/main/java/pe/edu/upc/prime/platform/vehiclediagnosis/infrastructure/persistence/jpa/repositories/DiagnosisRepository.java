package pe.edu.upc.prime.platform.vehiclediagnosis.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnostic, String> {
    /** Custom query method to check the existence of a diagnostic by id.
     *
     * @param diagnosticId the email to check for existence
     * @return true if a diagnostic with the given diagnostic exists, false otherwise
     */
    boolean existsById(String diagnosticId);

    List<Diagnostic> findByVehicleId_VehicleId(String vehicleId);
}
