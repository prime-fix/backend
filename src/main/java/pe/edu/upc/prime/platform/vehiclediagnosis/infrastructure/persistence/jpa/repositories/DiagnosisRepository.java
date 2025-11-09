package pe.edu.upc.prime.platform.vehiclediagnosis.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;

public interface DiagnosisRepository extends JpaRepository<Diagnostic, Long> {
}
