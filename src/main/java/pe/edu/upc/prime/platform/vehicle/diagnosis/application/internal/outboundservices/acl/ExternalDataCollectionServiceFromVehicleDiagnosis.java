package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.interfaces.acl.DataCollectionContextFacade;

/**
 * Service class for interacting with external Data Collection services via DataCollectionContextFacade.
 */
@Service
public class ExternalDataCollectionServiceFromVehicleDiagnosis {
    /**
     * The DataCollectionContextFacade for data collection operations.
     */
    private final DataCollectionContextFacade dataCollectionContextFacade;

    /**
     * Constructor for ExternalDataCollectionServiceFromVehicleDiagnosis.
     *
     * @param dataCollectionContextFacade the DataCollectionContextFacade to be used for data collection operations
     */
    public ExternalDataCollectionServiceFromVehicleDiagnosis(DataCollectionContextFacade dataCollectionContextFacade) {
        this.dataCollectionContextFacade = dataCollectionContextFacade;
    }

    /**
     * Check if a visit exists by its ID.
     *
     * @param visitId the ID of the visit to check
     * @return true if the visit exists, false otherwise
     */
    public boolean existsVisitById(Long visitId) {
        return this.dataCollectionContextFacade.existsVisitById(visitId);
    }
}
