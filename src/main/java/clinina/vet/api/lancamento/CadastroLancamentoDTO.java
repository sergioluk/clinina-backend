package clinina.vet.api.lancamento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record CadastroLancamentoDTO(
    Long id,
    String tipoReceita,
    String descricao,
    Long categoriaId,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date dataDaReceitaVencimento,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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