package pe.edu.upc.prime.platform.workshopCatalog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.CreateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.DeleteAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.UpdateAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.workshopCatalog.infrastructure.persistence.jpa.repositories.AutoRepairRepository;

import java.util.Optional;

/**
 * Implementation of AutoRepairCommandService
 */
@Service
public class AutoRepairCommandServiceImpl implements AutoRepairCommandService {

    private final AutoRepairRepository autoRepairRepository;

    /**
     * Constructor for AutoRepairCommandServiceImpl
     * @param autoRepairRepository the autoRepair repository
     */
    public AutoRepairCommandServiceImpl(AutoRepairRepository autoRepairRepository) {
        this.autoRepairRepository = autoRepairRepository;
    }


    @Override
    public String handle(CreateAutoRepairCommand command) {
        var autoRepair = new AutoRepair(command);

        try {
            this.autoRepairRepository.save(autoRepair);
        } catch (Exception ex) {
            throw  new IllegalArgumentException("Error while saving auto repair"+ ex.getMessage());
        }
        return autoRepair.getId().toString();
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
}
