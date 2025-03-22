package clinina.vet.api.produto;

import java.time.LocalDate;
import java.util.List;

public record ProdutoEstoqueDTO(
        Long id,
        String codigoDeBarras,
        String produto, // Nome do produto
        String categoria,
        List<String> imagens, // Lista de URLs das imagens
        String sabor,
        String idade,
        double preco,
        String peso,
        double desconto,
        String animal,
        int castrado,
        String porte,
        Double precoCompra,
        String fornecedor,
        int estoque,
        String imagemP,
        LocalDate dataVencimento // Campo da tabela Vencimento
) {
}
