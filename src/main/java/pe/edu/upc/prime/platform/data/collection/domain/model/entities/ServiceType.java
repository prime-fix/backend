package pe.edu.upc.center.data_collection.data.domain.model.valueobjects;

import java.util.Arrays;

public enum ServiceType {
    OTRO(1),
    MANTENIMIENTO_FRENOS(2),
    CAMBIO_ACEITE(3),
    DIAGNOSTICO_MOTOR(4),
    RECUPERACION_SUSPENCION(5),
    SISTEMA_ELECTRICO(6),
    ALINAEACION_Y_BALANCEO(7);
    private final int value;

    ServiceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ServiceType fromValue(int value) {
        return Arrays.stream(ServiceType.values())
                .filter(dt -> dt.value == value)
                .findFirst()
                .orElseThrow(()->
                        new IllegalArgumentException("Invalid value for ServiceType: " + value));
    }

    
}
