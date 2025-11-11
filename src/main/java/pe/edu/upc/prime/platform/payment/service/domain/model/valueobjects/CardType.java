package pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects;

import java.util.Arrays;
/**
 * Enumeration representing different types of card types.
 */
public enum CardType {
    VISA(0),
    MASTERCARD(1),
    AMEX(2);

    private final int value;

    CardType(int value) {this.value = value;}

    public int getValue() {return value;}

    public static CardType fromValue(int value) {
        return Arrays.stream(CardType.values())
                .filter(ct -> ct.value == value)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("[CardType] Invalid value for CardType: " + value));
    }
}
