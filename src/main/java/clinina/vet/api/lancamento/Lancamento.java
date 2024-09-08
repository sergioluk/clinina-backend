package clinina.vet.api.lancamento;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "lancamentos")
@Entity(name = "Lancamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_receita")
    private String tipoReceita;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "categoria_id")
    private Long categoriaId;
    @Column(name = "data_da_receita_vencimento")
    private Date dataDaReceitaVencimento;
    @Column(name = "data_recebimento_pagamento")
    private Date dataRecebimentoPagamento;
    @Column(name = "valor")
    private double valor;
    @Column(name = "quantidade_parcelas")
    private int quantidadeParcelas;

    public Lancamento(CadastroLancamentoDTO lancamentoDTO) {
        this.tipoReceita = lancamentoDTO.tipoReceita();
        this.descricao = lancamentoDTO.descricao();
        this.categoriaId = lancamentoDTO.categoriaId();
        this.dataDaReceitaVencimento = lancamentoDTO.dataDaReceitaVencimento();
        this.dataRecebimentoPagamento = lancamentoDTO.dataRecebimentoPagamento();
        this.valor = lancamentoDTO.valor();
        this.quantidadeParcelas = lancamentoDTO.quantidadeParcelas();
    }
}
