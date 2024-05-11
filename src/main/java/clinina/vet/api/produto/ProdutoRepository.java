package clinina.vet.api.produto;

import clinina.vet.api.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByCodigoDeBarras(String codigoDeBarras);

    List<Produto> findByProdutoContainingIgnoreCase(String nome);
}

