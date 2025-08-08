package clinina.vet.api.dto;

import java.time.LocalDate;

public record AnimalResumoDTO(
        Long id,
        String nome,
        String raca,
        LocalDate dataNascimento,
        String foto
) {
}
