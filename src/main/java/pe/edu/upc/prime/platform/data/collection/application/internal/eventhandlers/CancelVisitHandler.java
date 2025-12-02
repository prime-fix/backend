package pe.edu.upc.prime.platform.data.collection.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.application.internal.outboundservices.acl.ExternalVehicleDiagnosisServiceFromDataCollection;
import pe.edu.upc.prime.platform.data.collection.domain.model.events.CancelVisitEvent;

@Service
public class CancelVisitHandler {

    private final ExternalVehicleDiagnosisServiceFromDataCollection externalVehicleDiagnosisServiceFromDataCollection;

    public CancelVisitHandler(
            ExternalVehicleDiagnosisServiceFromDataCollection externalVehicleDiagnosisServiceFromDataCollection
    ){
        this.externalVehicleDiagnosisServiceFromDataCollection = externalVehicleDiagnosisServiceFromDataCollection;
    }

    @EventListener
    public void on(CancelVisitEvent cancelVisitEvent)
    {
        externalVehicleDiagnosisServiceFromDataCollection.deleteExpectedVisitByVisitId(cancelVisitEvent.getVisitId());
    }
}
