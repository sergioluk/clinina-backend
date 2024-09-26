package clinina.vet.api.lancamento.lancamentosdto;

import java.util.Date;
import java.util.List;

public record PaginaLancamentosDTO(
        double aReceber,
        double aPagar,
        double projecaoSaldo,
        double saldoAnterior,
        double saldoAtual,
        List<ListaLancamentosDTO> listaLancamentos,
        ListasPorcentagemCategoriasDTO listasPorcentagemCategorias,
        Date atualizadoEm
) {
}
