package clinina.vet.api.lancamento;

import java.util.Date;

public record CadastroLancamentoDTO(
    Long id,
    String tipoReceita,
    String descricao,
    Long categoriaId,
    Date dataDaReceitaVencimento,
    Date dataRecebimentoPagamento,
    double valor,
    int quantidadeParcelas
) {
    public CadastroLancamentoDTO(Lancamento lancamentoCriado) {
        this (
            lancamentoCriado.getId(),
            lancamentoCriado.getTipoReceita(),
            lancamentoCriado.getDescricao(),
            lancamentoCriado.getCategoriaId(),
            lancamentoCriado.getDataDaReceitaVencimento(),
            lancamentoCriado.getDataRecebimentoPagamento(),
            lancamentoCriado.getValor(),
            lancamentoCriado.getQuantidadeParcelas()
        );
    }
}