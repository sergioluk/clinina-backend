package clinina.vet.api.lancamento.lancamentosdto;

public record paginaLancamentoDTO(
        double totalReceber,
        double totalPagar,
        ListaLancamentosDTO lista
) {
}
