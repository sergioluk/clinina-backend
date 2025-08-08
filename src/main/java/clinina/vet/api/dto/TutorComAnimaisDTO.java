package clinina.vet.api.dto;

import java.util.List;

public record TutorComAnimaisDTO(
        Long id,
        String nome,
        String cpf,
        String celular,
        String telefone,
        List<AnimalResumoDTO> animais
) {
    public TutorComAnimaisDTO(Long id, String nome, String cpf, String celular, String telefone, List<AnimalResumoDTO> animais) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.celular = celular;
        this.telefone = telefone;
        this.animais = animais;
    }
}
