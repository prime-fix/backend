package pe.edu.upc.center.data_collection.data.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
@Embeddable
public record Service(@NotNull @NotBlank ServiceType type) {

    public Service {
        if(Objects.isNull(type) || type.toString().isBlank()){
            throw  new IllegalArgumentException("Service type cannot be blank or null");
        }
        if (type !=ServiceType.OTRO && type!= ServiceType.CAMBIO_ACEITE
        && type !=ServiceType.MANTENIMIENTO_FRENOS && type !=ServiceType.DIAGNOSTICO_MOTOR
        && type !=ServiceType.RECUPERACION_SUSPENCION && type !=ServiceType.SISTEMA_ELECTRICO
        && type !=ServiceType.ALINAEACION_Y_BALANCEO){
            throw new IllegalArgumentException("Service type cannot be blank or null");
        }
    }

    public Service(){
        this(null);
    }

}
