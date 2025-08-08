package clinina.vet.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoDTO(
        Long id,
        Long animalId,
        LocalDate data,
        LocalTime horaEntrada,
        LocalTime horaSaida,
        String tipo,
        String status,
        String descricao,
        String observacoes,
        List<Long> servicosIds
) {
}
