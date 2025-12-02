package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.application.internal.outboundservices.acl.ExternalIamServiceFromAutoRepairCatalog;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.*;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.AutoRepairRepository;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.ServiceRepository;

import java.util.Optional;

/**
 * Implementation of AutoRepairCommandService
 */
@Service
public class AutoRepairCommandServiceImpl implements AutoRepairCommandService {
    /**
     * The autoRepair repository
     */
    private final AutoRepairRepository autoRepairRepository;

    /**
     * The service repository
     */
    private final ServiceRepository serviceRepository;

    /**
     * The external IAM service from AutoRepair catalog
     */
    private final ExternalIamServiceFromAutoRepairCatalog externalIamServiceFromAutoRepairCatalog;

    /**
     * Constructor for AutoRepairCommandServiceImpl
     * @param autoRepairRepository the autoRepair repository
     * @param serviceRepository the service repository
     * @param externalIamServiceFromAutoRepairCatalog the external IAM service from AutoRepair catalog
     */
    public AutoRepairCommandServiceImpl(AutoRepairRepository autoRepairRepository,
                                        ServiceRepository serviceRepository,
                                        ExternalIamServiceFromAutoRepairCatalog externalIamServiceFromAutoRepairCatalog) {
        this.autoRepairRepository = autoRepairRepository;
        this.serviceRepository = serviceRepository;
        this.externalIamServiceFromAutoRepairCatalog = externalIamServiceFromAutoRepairCatalog;
    }

    /**
     * Handles the creation of a new auto repair based on the provided command.
     *
     * @param command the command containing the auto repair information
     * @return the ID of the newly created auto repair
     */
    @Override
    public Long handle(CreateAutoRepairCommand command) {
        // Validate if user account ID exists in external IAM service
        if (!this.externalIamServiceFromAutoRepairCatalog.exitsUserAccountById(command.userAccountId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[AutoRepairCommandServiceImpl] User Account ID: %s not found in the external IAM service",
                            command.userAccountId().value()));
        }

        // Create a new AutoRepair aggregate using the command data
        var autoRepair = new AutoRepair(command);
        try {
            this.autoRepairRepository.save(autoRepair);
        } catch (Exception e){
            throw new PersistenceException("[AutoRepairCommandServiceImpl] Error while saving new Auto Repair"
                    + e.getMessage());
        }
        return autoRepair.getId();
    }

    /**
     * Handles the update of an existing auto repair based on the provided command.
     *
     * @param command the command containing the updated auto repair information
     * @return an Optional containing the updated auto repair if successful, or empty if not found
     */
    @Override
    public Optional<AutoRepair> handle(UpdateAutoRepairCommand command) {
        var autoRepairId = command.autoRepairId();

        // Validate if AutoRepair ID exists
        if(!this.autoRepairRepository.existsById(autoRepairId)){
            throw new NotFoundIdException(AutoRepair.class, autoRepairId);
        }

        // Validate if user account ID exists in external IAM service
        if (!this.externalIamServiceFromAutoRepairCatalog.exitsUserAccountById(command.userAccountId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[AutoRepairCommandServiceImpl] User Account ID: %s not found in the external IAM service",
                            command.userAccountId().value()));
        }

        // Retrieve the existing AutoRepair and update its attributes
        var autoRepairUpdate = this.autoRepairRepository.findById(autoRepairId).get();
        autoRepairUpdate.updateAutoRepair(command);

        // Save the updated AutoRepair to the repository
        try {
            this.autoRepairRepository.save(autoRepairUpdate);
            return Optional.of(autoRepairUpdate);
        } catch (Exception ex) {
            throw new PersistenceException("[AutoRepairCommandServiceImpl] Error while saving auto repair"
                    + ex.getMessage());
        }
    }

    /**
     * Handles the deletion of an existing auto repair based on the provided command.
     *
     * @param command the command containing the ID of the AutoRepair to be related
     */
    @Override
    public void handle(DeleteAutoRepairCommand command) {
        if (!this.autoRepairRepository.existsById(command.autoRepairId())){
            throw new NotFoundIdException(AutoRepair.class, command.autoRepairId());
        }
        try {
            this.autoRepairRepository.deleteById(command.autoRepairId());
        } catch (Exception e) {
            throw new PersistenceException("[AutoRepairCommandServiceImpl] Error while deleting auto repair"
                    + e.getMessage());
        }
    }


    /**
     * Handles the addition of a service to an auto repair's service catalog based on the provided command.
     *
     * @param command The add service to AutoRepair Service catalog command containing the service id and auto repair id
     */
    @Override
    public void handle(AddServiceToAutoRepairServiceCatalogCommand command) {
        var service = serviceRepository.findById(command.serviceId())
                .orElseThrow(() -> new NotFoundIdException(Service.class, command.serviceId()));
        var autoRepair = autoRepairRepository.findById(command.autoRepairId())
                .orElseThrow(() -> new NotFoundIdException(AutoRepair.class, command.autoRepairId()));

        try {
            autoRepair.registerNewOffer(service, command.price(), command.is_active(), command.duration_hour());
            autoRepairRepository.save(autoRepair);

        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("Domain error while registering the offer: " + ex.getMessage());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error saving the auto repair offer: " + ex.getMessage());
        }
    }


    @Override
    public void handle(DeleteServiceToAutoRepairServiceCatalogCommand command) {
        var service = serviceRepository.findById(command.serviceId())
                .orElseThrow(() -> new NotFoundIdException(Service.class, command.serviceId()));

        var autoRepair = autoRepairRepository.findById(command.autoRepairId()).orElseThrow(()-> new NotFoundIdException(AutoRepair.class, command.autoRepairId()));
        try {
            autoRepair.removeOffer(service);
            autoRepairRepository.save(autoRepair);
        }catch (IllegalStateException e) {
            throw new IllegalArgumentException("Domain error while removing the offer: " + e.getMessage());
        }catch (Exception ex) {
            throw  new IllegalArgumentException("Error saving the auto repair offer: " + ex.getMessage());
        }
    }
}
