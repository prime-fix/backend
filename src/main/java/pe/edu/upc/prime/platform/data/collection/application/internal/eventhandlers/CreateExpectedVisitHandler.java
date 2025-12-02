package pe.edu.upc.prime.platform.data.collection.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.application.internal.outboundservices.acl.ExternalVehicleDiagnosisServiceFromDataCollection;
import pe.edu.upc.prime.platform.data.collection.domain.model.events.CreateExpectedVisitEvent;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateExpectedVisitCommand;


/**
 * Event handler responsible for reacting to CreateExpectedVisitEvent and
 * delegating the creation of the expected visit to the Vehicle Diagnosis
 * bounded context through the ACL.
 */
@Service
public class CreateExpectedVisitHandler {

    private final ExternalVehicleDiagnosisServiceFromDataCollection externalVehicleDiagnosisServiceFromDataCollection;


    /**
     * Constructor for CreateExpectedVisitEventHandler
     * @param externalVehicleDiagnosisServiceFromDataCollection Service responsible for interacting with the Vehicle Diagnosis context via ACL
     */
    public CreateExpectedVisitHandler(
            ExternalVehicleDiagnosisServiceFromDataCollection
                    externalVehicleDiagnosisServiceFromDataCollection
    ){
        this.externalVehicleDiagnosisServiceFromDataCollection = externalVehicleDiagnosisServiceFromDataCollection;
    }

    /**
     * Handles the CreateExpectedVisitEvent after an expected visit has been created
     * @param event event the domain event containing visit and vehicle data
     */
    @EventListener
    public void on(CreateExpectedVisitEvent event){
        externalVehicleDiagnosisServiceFromDataCollection.createExpectedVisit(event.getVisitId(), event.getVehicleId());
    }

}
