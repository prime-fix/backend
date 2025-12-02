package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairQueryService;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.events.TechnicianRegisteredEvent;

/**
 * Event handler for TechnicianRegisteredEvent.
 */
@Service
public class TechnicianRegisteredEventHandler {
    /**
     * Service to query Auto Repair data.
     */
    private final AutoRepairQueryService autoRepairQueryService;
    /**
     * Logger for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicianRegisteredEventHandler.class);

    /**
     * Constructor for TechnicianRegisteredEventHandler.
     *
     * @param autoRepairQueryService the service to query Auto Repair data
     */
    public TechnicianRegisteredEventHandler(AutoRepairQueryService autoRepairQueryService) {
        this.autoRepairQueryService = autoRepairQueryService;
    }

    /**
     * Handles the TechnicianRegisteredEvent.
     *
     * @param event the TechnicianRegisteredEvent to handle
     */
    @EventListener
    public void on(TechnicianRegisteredEvent event) {
        var optionalAutoRepair = this.autoRepairQueryService.handle(new GetAutoRepairByIdQuery(event.getAutoRepairId()));
        if (optionalAutoRepair.isPresent()) {
            var autoRepair = optionalAutoRepair.get();
            autoRepair.incrementTechniciansCount();
            LOGGER.info("Technician registered for Auto Repair ID {}:", event.getAutoRepairId());
            LOGGER.info("Current number of technicians: {}", autoRepair.getTechniciansCount());
        }  else {
            LOGGER.warn("Auto Repair with ID {} not found for Technician registration event.", event.getAutoRepairId());
        }
    }
}
