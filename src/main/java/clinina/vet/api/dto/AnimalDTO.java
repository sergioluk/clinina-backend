package clinina.vet.api.dto;

import java.time.LocalDate;

public record AnimalDTO(
        Long id,
        String nome,
        String especie,
        String sexo,
        String raca,
        String cor,
        String pelagem,
        LocalDate dataNascimento,
        String tamanho,
        Boolean castrado,
        String observacoes,
        String foto,
        Long tutorId,
        String tutorNome
) {
}
