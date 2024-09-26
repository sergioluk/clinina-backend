package clinina.vet.api.lancamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "SELECT l.id, l.tipo_receita, l.descricao, l.categoria_id, l.data_da_receita_vencimento, l.data_recebimento_pagamento, l.valor, l.quantidade_parcelas FROM lancamentos l where data_da_receita_vencimento >= CONCAT(:inicioAno, '-', :inicioMes, '-', :inicioDia, ' 00:00:00') and data_da_receita_vencimento <= CONCAT(:fimAno, '-', :fimMes, '-', :fimDia, ' 23:59:59');", nativeQuery = true)
    List<Lancamento> getLancamentosPorData(@Param("inicioDia") int inicioDia, @Param("inicioMes") int inicioMes, @Param("inicioAno") int inicioAno, @Param("fimDia") int fimDia, @Param("fimMes") int fimMes, @Param("fimAno") int fimAno);

    @Query(value = "SELECT " +
            "SUM(CASE WHEN l.tipo_receita = 'despesa' THEN l.valor ELSE 0 END) AS totalDespesas, " +
            "SUM(CASE WHEN l.tipo_receita = 'receita' THEN l.valor ELSE 0 END) AS totalReceitas, " +
            "SUM(CASE WHEN l.tipo_receita = 'despesa' AND l.data_da_receita_vencimento BETWEEN :localDateInicio AND :localDateFim THEN l.valor ELSE 0 END) AS totalDespesasPeriodo, " +
            "SUM(CASE WHEN l.tipo_receita = 'receita' AND l.data_da_receita_vencimento BETWEEN :localDateInicio AND :localDateFim THEN l.valor ELSE 0 END) AS totalReceitasPeriodo, " +
            "SUM(CASE WHEN l.tipo_receita = 'despesa' AND l.data_da_receita_vencimento < :localDateInicio THEN l.valor ELSE 0 END) AS totalDespesasPeriodoAnterior, " +
            "SUM(CASE WHEN l.tipo_receita = 'receita' AND l.data_da_receita_vencimento < :localDateInicio THEN l.valor ELSE 0 END) AS totalReceitasPeriodoAnterior, " +
            "SUM(CASE WHEN l.tipo_receita = 'despesa' AND l.data_da_receita_vencimento <= CURRENT_DATE() THEN l.valor ELSE 0 END) AS totalDespesasSaldoAtual, " +
            "SUM(CASE WHEN l.tipo_receita = 'receita' AND l.data_da_receita_vencimento <= CURRENT_DATE() THEN l.valor ELSE 0 END) AS totalReceitasSaldoAtual, " +
            "SUM(CASE WHEN l.tipo_receita = 'despesa' AND l.data_da_receita_vencimento > CURRENT_DATE() THEN l.valor ELSE 0 END) AS totalDespesasAPagar, " +
            "SUM(CASE WHEN l.tipo_receita = 'receita' AND l.data_da_receita_vencimento > CURRENT_DATE() THEN l.valor ELSE 0 END) AS totalReceitasSAReceber " +
            "FROM lancamentos l;" , nativeQuery = true)
    List<Object[]>  getLancamentoSums(@Param("localDateInicio") LocalDate localDateInicio, @Param("localDateFim") LocalDate localDateFim);

}