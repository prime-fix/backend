package pe.edu.upc.center.data_collection.data.domain.model.valueobjects;

public record ExpectedVisitId(Long expectedVisitId ) {
    public ExpectedVisitId{
        if(expectedVisitId <0 ){
            throw  new IllegalArgumentException("idExpectedVisit cannot be negative");
        }
    }

    public ExpectedVisitId(){
        this(0L);
    }
}
