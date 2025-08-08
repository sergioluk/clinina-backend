package clinina.vet.api.dto;

import java.time.LocalDate;

public record AnimalDetalhadoDTO(
        Long id,
        String nomePet,
        String raca,
        String pelagem,
        String tamanho,
        LocalDate dataNascimento,
        String nomeTutor,
        String foto,
        String cpf,
        String celular,
        String telefone
) {}
