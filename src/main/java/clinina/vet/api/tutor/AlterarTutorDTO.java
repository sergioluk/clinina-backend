package clinina.vet.api.tutor;

import clinina.vet.api.animais.Animais;
import clinina.vet.api.endereco.Endereco;

import java.util.List;

public record AlterarTutorDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String celular,
        Endereco endereco,
        List<Animais> animais
) {
}
