package clinina.vet.api.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
public interface VendaRepository extends JpaRepository<Venda, Long> {
    //@Query(value = "SELECT v.id, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id;", nativeQuery = true)
   //List<DadosItensVendidos> buscarItensVendidos();

    //@Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.pagamento, v.desconto, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where data >= '2024-04-30 00:00:00' and data <= '2024-04-30 23:59:59';", nativeQuery = true)
    //List<DadosItensVendidos> buscarItensVendidos(@Param("dia") int dia, @Param("mes") int mes, @Param("ano") int ano);

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.pagamento, v.desconto, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where data >= CONCAT(:start_ano, '-', :start_mes, '-', :start_dia, ' 00:00:00') and data <= CONCAT(:end_ano, '-', :end_mes, '-', :end_dia, ' 23:59:59');", nativeQuery = true)
    List<DadosItensVendidos> buscarItensVendidos(@Param("start_dia") int start_dia, @Param("start_mes") int start_mes, @Param("start_ano") int start_ano,@Param("end_dia") int end_dia, @Param("end_mes") int end_mes, @Param("end_ano") int end_ano);

    @Query(value = "SELECT SUM(preco_total) from vendas where data >= CONCAT(:start_ano, '-', :start_mes, '-', :start_dia, ' 00:00:00') and data <= CONCAT(:end_ano, '-', :end_mes, '-', :end_dia, ' 23:59:59');", nativeQuery = true)
    Double totalVendasLancamento(@Param("start_dia") int start_dia, @Param("start_mes") int start_mes, @Param("start_ano") int start_ano,@Param("end_dia") int end_dia, @Param("end_mes") int end_mes, @Param("end_ano") int end_ano);

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where v.iddevenda = :iddevenda ;", nativeQuery = true)
    List<DadosItensVendidos> encontrarItensPeloIdDeVenda(@Param("iddevenda") Long iddevenda);


    @Query(value = "SELECT * FROM vendas WHERE produto_id = :produtoId ;", nativeQuery = true)
    List<Venda> listaLinhaDoTempoTodos(@Param("produtoId") Long produtoId);

    @Query(value = "SELECT * FROM vendas WHERE produto_id = :produtoId AND data >= CURDATE() - INTERVAL :dias DAY;", nativeQuery = true)
    List<Venda>listaLinhaDoTempoComParam(@Param("produtoId") Long produtoId ,@Param("dias") int dias);

//    @Query(value = "SELECT v.data, v.preco_total from vendas v where data >= CONCAT(:start_ano, '-', :start_mes, '-', :start_dia, ' 00:00:00') and data <= CONCAT(:end_ano, '-', :end_mes, '-', :end_dia, ' 23:59:59') ORDER BY v.data DESC;", nativeQuery = true)
//    List<Venda> vendasDataTotal(@Param("start_dia") int start_dia, @Param("start_mes") int start_mes, @Param("start_ano") int start_ano,@Param("end_dia") int end_dia, @Param("end_mes") int end_mes, @Param("end_ano") int end_ano);

    @Query(value = "SELECT v.data, SUM(v.preco_total) FROM vendas v WHERE DATE(v.data) >= CONCAT(:start_ano, '-', :start_mes, '-', :start_dia, ' 00:00:00') AND DATE(v.data) <= CONCAT(:end_ano, '-', :end_mes, '-', :end_dia, ' 23:59:59') GROUP BY DATE(v.data) ORDER BY DATE(v.data) DESC", nativeQuery = true)
    List<Object[]> vendasDataTotal(@Param("start_dia") int start_dia, @Param("start_mes") int start_mes, @Param("start_ano") int start_ano, @Param("end_dia") int end_dia, @Param("end_mes") int end_mes, @Param("end_ano") int end_ano);

    @Query(value = "SELECT SUM(preco_total) FROM vendas WHERE data >= :dataInicial", nativeQuery = true)
    Double somaTotalVendas(@Param("dataInicial") String dataInicial);

    @Query(value = "SELECT " +
            "SUM(CASE WHEN v.data >= :dataInicial THEN v.preco_total ELSE 0 END) AS somaTotalVendas, " +
            "SUM(CASE WHEN v.data <= CURRENT_DATE() AND v.data >= :dataInicial THEN v.preco_total ELSE 0 END) AS somaTotalVendasAteData, " +
            "SUM(CASE WHEN v.data <= :localDateFim AND v.data >= :dataInicial THEN v.preco_total ELSE 0 END) AS somaTotalVendasPeriodo " +
            "FROM vendas v;" , nativeQuery = true)
    List<Object[]> somaTotalVendasTotalVendasMenorQueHoje(@Param("dataInicial") String dataInicial, @Param("localDateFim") LocalDate localDateFim);

    @Query(value = "SELECT DATE(v.data), SUM(v.preco_total) as valor " +
            "FROM vendas v " +
            "WHERE MONTH(v.data) = :mes " +
            "AND YEAR(v.data) = :ano " +
            "GROUP BY DATE(v.data)", nativeQuery = true)
    List<Object[]> totalVendasPorDiaNoMesGrafico(@Param("mes") int mes, @Param("ano") int ano);

    @Query(value = """
    SELECT *
    FROM vendas
    WHERE data >= STR_TO_DATE(
        CONCAT(:inicioAno, '-', :inicioMes, '-', :inicioDia, ' 00:00:00'),
        '%Y-%m-%d %H:%i:%s'
    )
    AND data <= STR_TO_DATE(
        CONCAT(:fimAno, '-', :fimMes, '-', :fimDia, ' 23:59:59'),
        '%Y-%m-%d %H:%i:%s'
    )
""", nativeQuery = true)
    List<Venda> getVendasEntreData(
            @Param("inicioDia") int inicioDia,
            @Param("inicioMes") int inicioMes,
            @Param("inicioAno") int inicioAno,
            @Param("fimDia") int fimDia,
            @Param("fimMes") int fimMes,
            @Param("fimAno") int fimAno
    );


}
