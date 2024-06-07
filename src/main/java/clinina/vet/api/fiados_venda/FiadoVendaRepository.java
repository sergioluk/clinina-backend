package clinina.vet.api.fiados_venda;

import clinina.vet.api.venda.DadosItensVendidos;
import clinina.vet.api.venda.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FiadoVendaRepository extends JpaRepository<FiadoVenda, Long> {
    @Query(value = "SELECT MAX(idvendafiado) from fiado_vendas;", nativeQuery = true)
    Long encontrarMaiorIdVenda();

    @Query(value = "SELECT v.id, v.peso, p.imagemP, p.produto, v.quantidade, v.preco_unitario as precoUnitario, v.data FROM fiado_vendas v JOIN produtos p ON v.produto_id = p.id where v.idvendafiado = :iddevenda ;", nativeQuery = true)
    List<DadosItensVendidos> encontrarItensPeloIdDeVenda(@Param("iddevenda") Long iddevenda);
}
