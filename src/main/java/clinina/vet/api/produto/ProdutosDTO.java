package clinina.vet.api.produto;

import clinina.vet.api.Idade.Idade;
import clinina.vet.api.categoria.Categoria;
import clinina.vet.api.fornecedor.Fornecedor;
import clinina.vet.api.sabor.Sabor;
import clinina.vet.api.vencimentos.Vencimento;

import java.time.LocalDate;
import java.util.List;

public record ProdutosDTO(
        String codigoDeBarras,
        Categoria categoria,
        String produto,
        Sabor sabor,
        Idade idade,
        double preco,
        String peso,
        double desconto,
        String animal,
        int castrado, //talvez mudar para int
        Fornecedor fornecedor,
        int estoque,
        String imagemP,
        Double precoCompra,

        LocalDate dataFabricacao, // Novo campo
        LocalDate dataVencimento  // Novo campo

) {
    public ProdutosDTO(Produto produto, Vencimento vencimento){
        this(
                produto.getCodigoDeBarras(),
                produto.getCategoria(),
                produto.getProduto(),
                produto.getSabor(),
                produto.getIdade(),
                produto.getPreco(),
                produto.getPeso(),
                produto.getDesconto(),
                produto.getAnimal(),
                produto.getCastrado(),
                produto.getFornecedor(),
                produto.getEstoque(),
                produto.getImagemP(),
                produto.getPrecoCompra(),
                vencimento != null ? vencimento.getDataFabricacao() : null, // Se vencimento não for nulo
                vencimento != null ? vencimento.getDataVencimento() : null  // Se vencimento não for nulo
        );
    }

}
