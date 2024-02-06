package clinina.vet.api.produto;

import clinina.vet.api.medico.Medico;

public record DadosListagemProduto(
        Long id,
        String produto,
        String codigoDeBarras,
        String categoria,
        String animal,
        String informacao,
        Double peso,
        Double preco,
        Double desconto,
        int estoque,
        int venda,
        String sabor,
        String idade,
        int castrado,
        String fornecedor,
        String litros,
        String imagemP,
        String imagens
        ) {

    //listagem
    /*
    public DadosListagemProduto(Produto produto){
        this(
                produto.getId(),
                produto.getProduto(),
                produto.getCodigoDeBarras(),
                produto.getCategoria(),
                produto.getAnimal(),
                //produto.getInformacao(),
                produto.getPeso(),
                produto.getPreco(),
                produto.getDesconto(),
                produto.getEstoque(),
                //produto.getVenda(),
                produto.getSabor(),
                produto.getIdade(),
                //produto.getCastrado(),
                produto.getFornecedor(),
                //produto.getLitros(),
                produto.getImagemP(),
                produto.getImagens()
        );
    }*/
}
