package pe.edu.upc.prime.platform.autorepair.register.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.application.internal.outboundservices.acl.ExternalAutoRepairCatalogServiceFromAutoRepairRegister;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.events.TechnicianDeletedEvent;

/**
 * Event handler for TechnicianDeletedEvent.
 */
@Service
public class TechnicianDeletedDomainEventHandler {
    /**
     * Service to interact with the Auto Repair Catalog.
     */
    private final ExternalAutoRepairCatalogServiceFromAutoRepairRegister externalAutoRepairCatalogService;
    /**
     * Logger for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicianDeletedDomainEventHandler.class);

    /**
     * Constructor for TechnicianDeletedDomainEventHandler.
     *
     * @param externalAutoRepairCatalogService the external service of Auto Repair Catalog
     */
    public TechnicianDeletedDomainEventHandler(ExternalAutoRepairCatalogServiceFromAutoRepairRegister externalAutoRepairCatalogService) {
        this.externalAutoRepairCatalogService = externalAutoRepairCatalogService;
    }

    /**
     * Handles the TechnicianDeletedEvent.
     *
     * @param event the TechnicianDeletedEvent to handle
     */
    @EventListener
    public void on(TechnicianDeletedEvent event) {
        var autoRepairId = event.getAutoRepairId();
        if (externalAutoRepairCatalogService.existsAutoRepairById(autoRepairId)) {
            var techniciansCount = externalAutoRepairCatalogService.decrementAutoRepairById(autoRepairId);
            LOGGER.info("Technician deleted for Auto Repair ID: {}", autoRepairId);
            LOGGER.info("Current number of technicians: {}", techniciansCount);
        }  else {
            LOGGER.warn("Auto Repair with ID {} not found for Technician deleted event.", autoRepairId);
        }
    }
}
