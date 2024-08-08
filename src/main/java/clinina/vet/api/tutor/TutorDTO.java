package clinina.vet.api.tutor;

import clinina.vet.api.animais.Animais;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.servicos.Servicos;

import java.util.List;

public record TutorDTO(
        String nome,
        String cpf,
        String telefone,
        String celular,
        Endereco endereco,
        List<Servicos> servicos,
        List<Animais> animais
) {
}
