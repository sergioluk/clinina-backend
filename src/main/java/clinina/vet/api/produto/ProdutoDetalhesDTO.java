package clinina.vet.api.produto;

import clinina.vet.api.venda.LinhaDoTempoDTO;

import java.util.List;

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
        String imagemP,
        Double valorVendaEstoque,
        Double valorCustoEstoque,
        List<LinhaDoTempoDTO> linhaDoTempo
) {
    public ProdutoDetalhesDTO(Produto produto, List<LinhaDoTempoDTO> lista){
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
                produto.getImagemP(),
                produto.getPreco() * produto.getEstoque(),
                produto.getPrecoCompra() != null ? produto.getPrecoCompra() * produto.getEstoque() : 0,
                lista
        );

    }

}
