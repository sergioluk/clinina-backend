package clinina.vet.api.venda;

import clinina.vet.api.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface VendaRepository extends JpaRepository<Venda, Long> {
    //@Query(value = "SELECT v.id, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id;", nativeQuery = true)
   //List<DadosItensVendidos> buscarItensVendidos();

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where data >= '2024-05-10 00:00:00' and data <= '2024-05-10 23:59:59';", nativeQuery = true)
    List<DadosItensVendidos> buscarItensVendidos();

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where v.iddevenda = :iddevenda ;", nativeQuery = true)
    List<DadosItensVendidos> encontrarItensPeloIdDeVenda(@Param("iddevenda") Long iddevenda);

}
