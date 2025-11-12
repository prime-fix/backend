package pe.edu.upc.prime.platform.workshopCatalog.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.*;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.WorkshopCatalogQueryService;
import pe.edu.upc.prime.platform.workshopCatalog.infrastructure.persistence.jpa.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopCatalogQueryServiceImpl implements WorkshopCatalogQueryService {

    private final AutoRepairRepository autoRepairRepository;
    private final LocationRepository locationRepository;
    private final TechnicianRepository technicianRepository;

    public WorkshopCatalogQueryServiceImpl(AutoRepairRepository autoRepairRepository,
                                           LocationRepository locationRepository,
                                           TechnicianRepository technicianRepository) {
        this.autoRepairRepository = autoRepairRepository;
        this.locationRepository = locationRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<AutoRepair> handle(GetAllAutoRepairsQuery query) {
        return this.autoRepairRepository.findAll();
    }

    @Override
    public Optional<AutoRepair> handle(GetAutoRepairByIdQuery query) {
        return this.autoRepairRepository.findById(query.repairId());
    }

    @Override
    public List<Location> handle(GetAllLocationsQuery query) {
        return this.locationRepository.findAll();
    }

    @Override
    public List<Technician> handle(GetAllTechniciansQuery query) {
        return this.technicianRepository.findAll();
    }
}
