package clinina.vet.api.tutor;

import clinina.vet.api.animais.Animais;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.mensagem.Mensagem;
import clinina.vet.api.servicos.Servicos;

import java.util.List;

public record TutorDTO(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String celular,
        Endereco endereco,
        List<Servicos> servicos,
        List<Animais> animais
) {
    public TutorDTO(Tutor t){
        this(
                t.getId(),
                t.getNome(),
                t.getCpf(),
                t.getTelefone(),
                t.getCelular(),
                t.getEndereco(),
                t.getServicos(),
                t.getAnimais()
        );
    }
}
