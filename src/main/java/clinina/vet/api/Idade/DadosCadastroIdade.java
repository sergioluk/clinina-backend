package clinina.vet.api.Idade;

import clinina.vet.api.categoria.Categoria;

public record DadosCadastroIdade(
        Long id,
        String nome
) {
    public DadosCadastroIdade(Idade idade){
        this(
                idade.getId(),
                idade.getNome()
        );
    }
}
