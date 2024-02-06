package clinina.vet.api.produto;

import clinina.vet.api.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByCodigoDeBarras(String codigoDeBarras);

}

