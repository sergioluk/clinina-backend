package clinina.vet.api.lancamento.lancamentosdto;

import java.util.Date;
import java.util.List;

public record ListaLancamentosDTO(
        Date data,
        List<LancamentosDTO> lancamentos
) {
}
