package pe.edu.upc.prime.platform.payment.service.domain.model.queries;


import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdUserAccount;

import java.util.Objects;

public record GetPaymentByIdUserAccountQuery(IdUserAccount idUserAccount) {

    public GetPaymentByIdUserAccountQuery {
        Objects.requireNonNull(idUserAccount, "[GetPaymentByIdUserAccountQuery] idUserAccount cannot be null");
    }

}
