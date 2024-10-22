package clinina.vet.api.venda;

import java.util.Date;

public record GraficoDTO(
        Date data,
        double total_vendas
) {
}
