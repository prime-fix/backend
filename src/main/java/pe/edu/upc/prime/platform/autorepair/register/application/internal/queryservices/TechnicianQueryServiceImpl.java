package pe.edu.upc.prime.platform.autorepair.register.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianQueryService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TechnicianQueryService interface.
 */
@Service
public class TechnicianQueryServiceImpl implements TechnicianQueryService {

    private final TechnicianRepository technicianRepository;

    public TechnicianQueryServiceImpl(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<Technician> handleGetAll() {
        return technicianRepository.findAll();
    }

    @Override
    public Optional<Technician> handleGetById(String idTechnician) {
        return Optional.ofNullable(
                technicianRepository.findById(idTechnician)
                        .orElseThrow(() -> new NotFoundIdException(Technician.class, idTechnician))
        );
    }

    @Override
    public List<Technician> handleGetByAutoRepairId(long idAutoRepair) {
        return technicianRepository.findByIdAutoRepair(new IdAutoRepair(idAutoRepair));
    }
}
