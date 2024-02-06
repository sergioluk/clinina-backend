package clinina.vet.api.produto;

public record DadosProduto(
        Long id,
        String produto,
        String codigoDeBarras,
        String categoria,
        String animal,
        String informacao,
        double peso,
        double preco,
        double desconto,
        int qtd_estoque,
        int qtd_vendido
) {
}
