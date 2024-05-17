package clinina.vet.api.produto;

import clinina.vet.api.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByCodigoDeBarras(String codigoDeBarras);

    List<Produto> findByProdutoContainingIgnoreCase(String nome);

    List<Produto> findByCodigoDeBarrasContainingIgnoreCase(String nome);

    boolean existsByCodigoDeBarras(String codigoDeBarras);

    //@Query(value = "SELECT p FROM produtos p WHERE p.produto LIKE %:nome% OR p.codigo_de_Barras LIKE %:nome%", nativeQuery = true)
    //@Query(value = "SELECT * FROM produtos WHERE produto LIKE %:nome% OR codigo_de_Barras LIKE %:nome%", nativeQuery = true)
    //List<Produto> searchByNomeOrCodigoBarras (@Param("nome") String nome);
}

