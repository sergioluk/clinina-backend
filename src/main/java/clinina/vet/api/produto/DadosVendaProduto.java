package clinina.vet.api.produto;

public record DadosVendaProduto(
        long id,
        String codigoDeBarras,
        String produto,
        Double preco,
        String imagemP,
        String peso,
        int quantidade,
        Double desconto
) {
}
