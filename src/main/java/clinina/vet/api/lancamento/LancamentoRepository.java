package clinina.vet.api.lancamento;

import clinina.vet.api.caixa.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "SELECT l.id, l.tipo_receita, l.descricao, l.categoria_id, l.data_da_receita_vencimento, l.data_recebimento_pagamento, l.valor, l.quantidade_parcelas FROM lancamentos l where data_da_receita_vencimento >= CONCAT(:inicioAno, '-', :inicioMes, '-', :inicioDia, ' 00:00:00') and data_da_receita_vencimento <= CONCAT(:fimAno, '-', :fimMes, '-', :fimDia, ' 23:59:59');", nativeQuery = true)
    List<Lancamento> getLancamentosPorData(@Param("inicioDia") int inicioDia, @Param("inicioMes") int inicioMes, @Param("inicioAno") int inicioAno, @Param("fimDia") int fimDia, @Param("fimMes") int fimMes, @Param("fimAno") int fimAno);

}