package pe.edu.upc.prime.platform.workshopCatalog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.*;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.WorkshopCatalogCommandService;
import pe.edu.upc.prime.platform.workshopCatalog.infrastructure.persistence.jpa.repositories.*;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class WorkshopCatalogCommandServiceImpl implements WorkshopCatalogCommandService {

    private final AutoRepairRepository autoRepairRepository;
    private final LocationRepository locationRepository;
    private final TechnicianRepository technicianRepository;

    public WorkshopCatalogCommandServiceImpl(AutoRepairRepository autoRepairRepository,
                                             LocationRepository locationRepository,
                                             TechnicianRepository technicianRepository) {
        this.autoRepairRepository = autoRepairRepository;
        this.locationRepository = locationRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public Long handle(CreateAutoRepairCommand command) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            var repairDate = sdf.parse(command.repairDate());
            var autoRepair = new AutoRepair(command.customerName(), repairDate, command.repairTime(), command.description());
            this.autoRepairRepository.save(autoRepair);
            return autoRepair.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating auto repair: " + e.getMessage());
        }
    }

    @Override
    public Optional<AutoRepair> handle(UpdateAutoRepairStatusCommand command) {
        if (!this.autoRepairRepository.existsById(command.repairId())) {
            throw new IllegalArgumentException("Auto repair not found");
        }

        var autoRepair = this.autoRepairRepository.findById(command.repairId()).get();
        switch (command.status()) {
            case "ACCEPTED":
                autoRepair.acceptRepair();
                break;
            case "REJECTED":
                autoRepair.rejectRepair();
                break;
            case "COMPLETED":
                autoRepair.completeRepair();
                break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }

        autoRepair = this.autoRepairRepository.save(autoRepair);
        return Optional.of(autoRepair);
    }

    @Override
    public Long handle(CreateLocationCommand command) {
        var location = new Location(command.name(), command.address(), command.phone(), command.openingHours());
        this.locationRepository.save(location);
        return location.getId();
    }

    @Override
    public Long handle(CreateTechnicianCommand command) {
        var technician = new Technician(command.name(), command.specialty(), command.available());
        this.technicianRepository.save(technician);
        return technician.getId();
    }
}
