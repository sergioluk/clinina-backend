package clinina.vet.api.caixa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    @Query(value = "SELECT c.id, c.abertura_data, c.abertura_valor, c.despesas_caixa, c.entrada, c.fechamento_caixa_data, c.fechamento_caixa_valor, c.credito_conferido, c.debito_conferido, c.dinheiro_conferido, c.pix_conferido, c.fiado_conferido FROM caixa c where abertura_data >= CONCAT(:ano, '-', :mes, '-', :dia, ' 00:00:00') and abertura_data <= CONCAT(:ano, '-', :mes, '-', :dia, ' 23:59:59');", nativeQuery = true)
    Caixa buscarCaixa(@Param("dia") int dia, @Param("mes") int mes, @Param("ano") int ano);

}
