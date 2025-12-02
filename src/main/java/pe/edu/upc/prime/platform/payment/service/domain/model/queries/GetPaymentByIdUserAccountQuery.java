package pe.edu.upc.prime.platform.payment.service.domain.model.queries;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

import java.util.Objects;

/**
 * Query to get payments by user account ID.
 *
 * @param idUserAccount the ID of the user account to retrieve payments for
 */
public record GetPaymentByIdUserAccountQuery(UserAccountId idUserAccount) {

    public GetPaymentByIdUserAccountQuery {
        Objects.requireNonNull(idUserAccount, "[GetPaymentByIdUserAccountQuery] idUserAccount cannot be null");
    }

}
