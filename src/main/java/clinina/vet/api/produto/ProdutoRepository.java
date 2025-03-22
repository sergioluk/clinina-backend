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

    @Query(value = """
    SELECT 
        p.id,
        CASE WHEN :codigoBarras = 1 THEN p.codigo_de_Barras ELSE NULL END,
        CASE WHEN :produto = 1 THEN p.produto ELSE NULL END,
        CASE WHEN :categoria = 1 THEN p.categoria_id ELSE NULL END,
        CASE WHEN :imagens = 1 THEN i.imagem ELSE NULL END, -- Adicionei o parâmetro correto!
        CASE WHEN :sabor = 1 THEN p.sabor_id ELSE NULL END,
        CASE WHEN :idade = 1 THEN p.idade_id ELSE NULL END,
        CASE WHEN :preco = 1 THEN p.preco ELSE NULL END,
        CASE WHEN :peso = 1 THEN p.peso ELSE NULL END,
        CASE WHEN :desconto = 1 THEN p.desconto ELSE NULL END,
        CASE WHEN :animal = 1 THEN p.animal ELSE NULL END,
        CASE WHEN :castrado = 1 THEN p.castrado ELSE NULL END,
        CASE WHEN :porte = 1 THEN p.porte ELSE NULL END,
        CASE WHEN :precoCompra = 1 THEN p.preco_compra ELSE NULL END,
        CASE WHEN :fornecedor = 1 THEN p.fornecedor_id ELSE NULL END,
        CASE WHEN :estoque = 1 THEN p.estoque ELSE NULL END,
        CASE WHEN :imagemP = 1 THEN p.imagemP ELSE NULL END,
        CASE WHEN :dataVencimento = 1 THEN v.data_vencimento ELSE NULL END
    FROM produtos p
    LEFT JOIN vencimentos v ON p.id = v.id_produto
    LEFT JOIN imagens i ON i.produto_id = p.id
    ORDER BY v.data_vencimento IS NULL, v.data_vencimento ASC
    """, nativeQuery = true)
    List<Object[]> buscarProdutosSQL(
            @Param("codigoBarras") int codigoBarras,
            @Param("produto") int produto,
            @Param("categoria") int categoria,
            @Param("imagens") int imagens, // ✅ Adicionei este parâmetro!
            @Param("sabor") int sabor,
            @Param("idade") int idade,
            @Param("preco") int preco,
            @Param("peso") int peso,
            @Param("desconto") int desconto,
            @Param("animal") int animal,
            @Param("castrado") int castrado,
            @Param("porte") int porte,
            @Param("precoCompra") int precoCompra,
            @Param("fornecedor") int fornecedor,
            @Param("estoque") int estoque,
            @Param("imagemP") int imagemP,
            @Param("dataVencimento") int dataVencimento
    );






    //@Query(value = "SELECT p FROM produtos p WHERE p.produto LIKE %:nome% OR p.codigo_de_Barras LIKE %:nome%", nativeQuery = true)
    //@Query(value = "SELECT * FROM produtos WHERE produto LIKE %:nome% OR codigo_de_Barras LIKE %:nome%", nativeQuery = true)
    //List<Produto> searchByNomeOrCodigoBarras (@Param("nome") String nome);
}

