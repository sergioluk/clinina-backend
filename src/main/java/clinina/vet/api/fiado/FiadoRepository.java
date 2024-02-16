package clinina.vet.api.fiado;

import clinina.vet.api.venda.DadosItensVendidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FiadoRepository extends JpaRepository<Fiado, Long> {

    @Query(value = "SELECT MAX(iddevenda) from vendas;", nativeQuery = true)
    Long encontrarMaiorIdVenda();


}
