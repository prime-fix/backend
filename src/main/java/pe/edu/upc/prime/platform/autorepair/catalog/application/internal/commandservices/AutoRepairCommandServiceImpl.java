package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.AddServiceToAutoRepairServiceCatalogCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.AutoRepairRepository;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.ServiceRepository;

import java.util.Optional;

/**
 * Implementation of AutoRepairCommandService
 */
@Service
public class AutoRepairCommandServiceImpl implements AutoRepairCommandService {

    private final AutoRepairRepository autoRepairRepository;
    private final ServiceRepository serviceRepository;
    /**
     * Constructor for AutoRepairCommandServiceImpl
     * @param autoRepairRepository the autoRepair repository
     */
    public AutoRepairCommandServiceImpl(AutoRepairRepository autoRepairRepository, ServiceRepository serviceRepository) {
        this.autoRepairRepository = autoRepairRepository;
        this.serviceRepository = serviceRepository;
    }


    @Override
    public Long handle(CreateAutoRepairCommand command) {
        var autoRepair = new AutoRepair(command);
        try{
            this.autoRepairRepository.save(autoRepair);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving new Auto Repair");
        }
        return autoRepair.getId();
    }

    @Override
    public Optional<AutoRepair> handle(UpdateAutoRepairCommand command) {
        var autoRepairId = command.autoRepairId();
        if(!this.autoRepairRepository.existsById(Long.valueOf(autoRepairId))){
            throw new NotFoundIdException(AutoRepair.class, autoRepairId);
        }
        var autoRepairUpdate = this.autoRepairRepository.findById(Long.valueOf(autoRepairId)).get();
        autoRepairUpdate.updateAutoRepair(command);

        try {
            this.autoRepairRepository.save(autoRepairUpdate);
            return Optional.of(autoRepairUpdate);
        } catch (Exception ex) {
            throw  new IllegalArgumentException("Error while saving auto repair"+ ex.getMessage());
        }
    }

    @Override
    public void handle(DeleteAutoRepairCommand command) {
        if (!this.autoRepairRepository.existsById(Long.valueOf(command.autoRepairId()))){
            throw new IllegalArgumentException("Auto repair id not found" + command.autoRepairId());
        }
        try {
            this.autoRepairRepository.deleteById(Long.valueOf(command.autoRepairId()));
        } catch (Exception ex) {
            throw   new IllegalArgumentException("Error while deleting auto repair"+ ex.getMessage());
        }
    }

    @Override
    public void handle(AddServiceToAutoRepairServiceCatalogCommand command) {
        var service = serviceRepository.findById(command.serviceId())
                .orElseThrow(() -> new NotFoundIdException(Service.class, command.serviceId().toString()));

        var autoRepair = autoRepairRepository.findById(command.autoRepairId())
                .orElseThrow(() -> new NotFoundIdException(AutoRepair.class, command.autoRepairId().toString()));

        try {
            autoRepair.registerNewOffer(service, command.price());
            autoRepairRepository.save(autoRepair);

        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("Error de dominio al registrar oferta: " + ex.getMessage());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error al guardar la oferta del taller: " + ex.getMessage());
        }
    }
}
