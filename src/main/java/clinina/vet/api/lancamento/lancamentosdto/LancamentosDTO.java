package clinina.vet.api.lancamento.lancamentosdto;

import java.util.Date;

public record LancamentosDTO(
        Long id,
        Date dataDaReceitaVencimento,
        Date dataRecebimentoPagamento,
        String status,
        String descricao,
        String categoriaNome,
        double valor,
        String tipoReceita
) {
    public LancamentosDTO(Long id, Date dataDaReceitaVencimento,Date dataRecebimentoPagamento, String status, String descricao, String categoriaNome, double valor, String tipoReceita) {
        this.id = id;
        this.dataDaReceitaVencimento = dataDaReceitaVencimento;
        this.dataRecebimentoPagamento = dataRecebimentoPagamento;
        this.status = status;
        this.descricao = descricao;
        this.categoriaNome = categoriaNome;
        this.valor = valor;
        this.tipoReceita = tipoReceita;
    }
}
