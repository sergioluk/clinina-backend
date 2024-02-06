package clinina.vet.api.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface VendaRepository extends JpaRepository<Venda, Long> {
    //@Query(value = "SELECT v.id, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id;", nativeQuery = true)
   //List<DadosItensVendidos> buscarItensVendidos();

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.preco_total as PrecoTotal, v.data FROM vendas v JOIN produtos p ON v.produto_id = p.id where data >= '2024-02-05 00:00:00' and data <= '2024-02-05 23:59:59';", nativeQuery = true)
    List<DadosItensVendidos> buscarItensVendidos();
}
