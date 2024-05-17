package clinina.vet.api.produto;

import clinina.vet.api.Idade.Idade;
import clinina.vet.api.categoria.Categoria;
import clinina.vet.api.categoria.DadosCadastroCategoria;
import clinina.vet.api.fornecedor.Fornecedor;
import clinina.vet.api.imagens.Imagem;
import clinina.vet.api.informacoes.Informacao;
import clinina.vet.api.sabor.Sabor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DadosCadastroProduto(
        String codigoDeBarras,
        Categoria categoria,
        String produto,
        List<String> imagens,
        Sabor sabor,
        Idade idade,
        double preco,
        String peso,
        double desconto,
        String animal,
        int castrado, //talvez mudar para int
        List<String>  porte,
        List<String> informacao,
        Fornecedor fornecedor,
        int estoque,
        String imagemP,
        Double precoCompra
) {


}
