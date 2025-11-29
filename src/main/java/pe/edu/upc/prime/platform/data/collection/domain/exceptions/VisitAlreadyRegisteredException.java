package pe.edu.upc.prime.platform.data.collection.domain.exceptions;

public class VisitAlreadyRegisteredException extends RuntimeException {

    public VisitAlreadyRegisteredException(Long visitId, Long autoRepairId) {
        super(String.format("Visit with id " + visitId + " already registered in "+ autoRepairId));
    }

}
