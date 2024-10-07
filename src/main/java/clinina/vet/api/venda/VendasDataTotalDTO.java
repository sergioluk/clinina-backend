package clinina.vet.api.venda;

import java.util.Date;

public record VendasDataTotalDTO(
        Date data,
        java.math.BigDecimal precoTotal) {
}
