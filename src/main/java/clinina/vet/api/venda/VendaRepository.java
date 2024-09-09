package clinina.vet.api.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface VendaRepository extends JpaRepository<Venda, Long> {
    //@Query(value = "SELECT v.id, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id;", nativeQuery = true)
   //List<DadosItensVendidos> buscarItensVendidos();

    //@Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.pagamento, v.desconto, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where data >= '2024-04-30 00:00:00' and data <= '2024-04-30 23:59:59';", nativeQuery = true)
    //List<DadosItensVendidos> buscarItensVendidos(@Param("dia") int dia, @Param("mes") int mes, @Param("ano") int ano);

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.pagamento, v.desconto, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where data >= CONCAT(:start_ano, '-', :start_mes, '-', :start_dia, ' 00:00:00') and data <= CONCAT(:end_ano, '-', :end_mes, '-', :end_dia, ' 23:59:59');", nativeQuery = true)
    List<DadosItensVendidos> buscarItensVendidos(@Param("start_dia") int start_dia, @Param("start_mes") int start_mes, @Param("start_ano") int start_ano,@Param("end_dia") int end_dia, @Param("end_mes") int end_mes, @Param("end_ano") int end_ano);

    @Query(value = "SELECT SUM(preco_total) from vendas where data >= CONCAT(:start_ano, '-', :start_mes, '-', :start_dia, ' 00:00:00') and data <= CONCAT(:end_ano, '-', :end_mes, '-', :end_dia, ' 23:59:59');", nativeQuery = true)
    double totalVendasLancamento(@Param("start_dia") int start_dia, @Param("start_mes") int start_mes, @Param("start_ano") int start_ano,@Param("end_dia") int end_dia, @Param("end_mes") int end_mes, @Param("end_ano") int end_ano);

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where v.iddevenda = :iddevenda ;", nativeQuery = true)
    List<DadosItensVendidos> encontrarItensPeloIdDeVenda(@Param("iddevenda") Long iddevenda);


    @Query(value = "SELECT * FROM vendas WHERE produto_id = :produtoId ;", nativeQuery = true)
    List<Venda> listaLinhaDoTempoTodos(@Param("produtoId") Long produtoId);

    @Query(value = "SELECT * FROM vendas WHERE produto_id = :produtoId AND data >= CURDATE() - INTERVAL :dias DAY;", nativeQuery = true)
    List<Venda>listaLinhaDoTempoComParam(@Param("produtoId") Long produtoId ,@Param("dias") int dias);

}
