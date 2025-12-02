package pe.edu.upc.prime.platform.data.collection.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.acl.VehicleDiagnosisContextFacade;

/**
 * Service class for interacting with external Vehicle Diagnosis services via VehicleDiagnosisContextFacade.
 */
@Service
public class ExternalVehicleDiagnosisServiceFromDataCollection {
    /**
     * The VehicleDiagnosisContextFacade for vehicle diagnosis operations.
     */
    private final VehicleDiagnosisContextFacade vehicleDiagnosisContextFacade;

    /**
     * Constructor for ExternalVehicleDiagnosisService.
     *
     * @param vehicleDiagnosisContextFacade the VehicleDiagnosisContextFacade to be used for vehicle diagnosis operations
     */
    public ExternalVehicleDiagnosisServiceFromDataCollection(VehicleDiagnosisContextFacade vehicleDiagnosisContextFacade) {
        this.vehicleDiagnosisContextFacade = vehicleDiagnosisContextFacade;
    }

    /**
     * Create an expected visit based on the provided visit ID.
     *
     * @param visitId the ID of the visit for which to create an expected visit
     * @return the ID of the created expected visit
     */
    public Long createExpectedVisit(Long visitId, Long vehicleId) {
        return this.vehicleDiagnosisContextFacade.createExpectedVisit(visitId, vehicleId);
    }
}
