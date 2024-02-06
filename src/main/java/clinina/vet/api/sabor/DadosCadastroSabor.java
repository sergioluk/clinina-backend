package clinina.vet.api.sabor;

import clinina.vet.api.Idade.Idade;

public record DadosCadastroSabor(
        Long id,
        String nome
) {
    public DadosCadastroSabor(Sabor sabor){
        this(
                sabor.getId(),
                sabor.getNome()
        );
    }
}
