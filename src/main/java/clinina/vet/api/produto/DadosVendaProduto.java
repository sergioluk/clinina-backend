package clinina.vet.api.produto;

import clinina.vet.api.medico.Medico;

public record DadosVendaProduto(
        long id,
        String codigoDeBarras,
        String produto,
        Double preco,
        String imagemP,
        String peso,
        int quantidade,
        Double desconto,
        Double precoCompra
) {
    public DadosVendaProduto(Produto produto){
        this(produto.getId(), produto.getCodigoDeBarras(), produto.getProduto(), produto.getPreco(), produto.getImagemP(), produto.getPeso(), 1, produto.getDesconto(), produto.getPrecoCompra());
    }
}
