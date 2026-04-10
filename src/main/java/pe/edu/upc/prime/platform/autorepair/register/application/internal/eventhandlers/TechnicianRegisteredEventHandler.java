package pe.edu.upc.prime.platform.autorepair.register.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.upc.prime.platform.autorepair.register.application.internal.outboundservices.acl.ExternalAutoRepairCatalogServiceFromAutoRepairRegister;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.events.TechnicianRegisteredEvent;

/**
 * Event handler for TechnicianRegisteredEvent.
 */
@Service
public class TechnicianRegisteredEventHandler {
    /**
     * Service to interact with the Auto Repair Catalog.
     */
    private final ExternalAutoRepairCatalogServiceFromAutoRepairRegister externalAutoRepairCatalogService;
    /**
     * Logger for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicianRegisteredEventHandler.class);

    /**
     * Constructor for TechnicianRegisteredEventHandler.
     *
     * @param externalAutoRepairCatalogService the external service of Auto Repair Catalog
     */
    public TechnicianRegisteredEventHandler(ExternalAutoRepairCatalogServiceFromAutoRepairRegister externalAutoRepairCatalogService) {
        this.externalAutoRepairCatalogService = externalAutoRepairCatalogService;
    }

    /**
     * Handles the TechnicianRegisteredEvent.
     *
     * @param event the TechnicianRegisteredEvent to handle
     */
    @EventListener
    public void on(TechnicianRegisteredEvent event) {
        var autoRepairId = event.getAutoRepairId();
        if (externalAutoRepairCatalogService.existsAutoRepairById(autoRepairId)) {
            var techniciansCount = externalAutoRepairCatalogService.incrementAutoRepairById(autoRepairId);
            LOGGER.info("Technician registered for Auto Repair ID {}:", autoRepairId);
            LOGGER.info("Current number of technicians: {}", techniciansCount);
        }  else {
            LOGGER.warn("Auto Repair with ID {} not found for Technician registration event.", autoRepairId);
        }
    }
}
