package pe.edu.upc.prime.platform.data.collection.domain.exceptions;

public class VisitNotFoundException extends RuntimeException{

    public VisitNotFoundException(Long visitId){
        super(String.format("Visit with id %s does not exist", visitId));
    }

    public VisitNotFoundException(Long visitId, Throwable cause){
        super(String.format("Visit with id %s does not exist", visitId), cause);
    }

}
