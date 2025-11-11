package pe.edu.upc.prime.platform.data.collection.domain.exceptions;

public class ServiceNotFoundException extends RuntimeException{

    public ServiceNotFoundException(Long serviceId){
        super(String.format("Service with id %s does not exist", serviceId));
    }

    public ServiceNotFoundException(Long serviceId, Throwable cause){
        super(String.format("Service with id %s does not exist", serviceId), cause);
    }
}
