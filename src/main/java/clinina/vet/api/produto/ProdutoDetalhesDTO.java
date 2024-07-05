package clinina.vet.api.produto;

public record ProdutoDetalhesDTO(
        Long id,
        String produto,
        String codigoDeBarras,
        String categoria,
        String sabor,
        String idade,
        Double precoCompra,
        Double preco,
        Double desconto,
        int estoque,
        int quantidadeVendido,
        String animal,
        String peso,
        String porte,
        String fornecedor,
        int castrado,
        String imagemP
) {
    public ProdutoDetalhesDTO(Produto produto){
        this(produto.getId(),
                produto.getProduto(),
                produto.getCodigoDeBarras(),
                produto.getCategoria().getNome(),
                produto.getSabor().getNome(),
                produto.getIdade().getNome(),
                produto.getPrecoCompra(),
                produto.getPreco(),
                produto.getDesconto(),
                produto.getEstoque(),
                0,
                produto.getAnimal(),
                produto.getPeso(),
                produto.getPorte(),
                produto.getFornecedor().getNome(),
                produto.getCastrado(),
                produto.getImagemP()
        );

    }
}
