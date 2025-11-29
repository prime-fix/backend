package pe.edu.upc.prime.platform.data.collection.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.events.VisitCreatedEvent;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetVisitByIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitCommandService;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitQueryService;

/**
 * Ee¿vent Handler responsible for reacting to a successful VisitCreatedEvent
 */
@Service
public class VisitCreatedEventHandler {
    private final VisitCommandService visitCommandService;
    private final VisitQueryService visitQueryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitCreatedEventHandler.class);

    /**
     * Constructor for VisitCreatedEventHandler
     *
     * @param visitCommandService Service to execute commands on the Visit aggregate
     * @param visitQueryService   Service to query the Visit aggregate
     */
    public VisitCreatedEventHandler(VisitCommandService visitCommandService, VisitQueryService visitQueryService) {
        this.visitCommandService = visitCommandService;
        this.visitQueryService = visitQueryService;
    }

    /**
     * Handles the VisitCreatedEvent after a new visit has been successfully created
     * @param event the VisitCreatedEvent containing the ID and details of the created visit
     */
    @EventListener
    public void on(VisitCreatedEvent event) {
        var getVisitByIdQuery = new GetVisitByIdQuery(event.getVisitId());
        var visit = visitQueryService.handle(getVisitByIdQuery);

        if (visit.isPresent()){
            LOGGER.info("Visit successfully assigned with ID: {}", event.getVisitId());
        } else {
            LOGGER.warn("Error: The visit with ID could not be assigned: {}", event.getVisitId());
        }
    }
}
