package clinina.vet.api.imagens;

import clinina.vet.api.produto.Produto;

import java.util.List;

public record DadosCadastroImagem(

        Produto produto,
        List<String> imagens
        ) {
}
