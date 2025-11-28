package pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing an IdUserAccount.
 */
@Embeddable
public record IdUserAccount(String idUserAccount) {
    public IdUserAccount {
        if (Objects.isNull(idUserAccount) || idUserAccount.isBlank()) {
            throw new IllegalArgumentException("[IdUserAccount] IdUserAccount cannot be null or blank");
        }
    }

    public IdUserAccount(){this(null);}

    public String getIdUserAccount() {
        return idUserAccount;
    }
}
