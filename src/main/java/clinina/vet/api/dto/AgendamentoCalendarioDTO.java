package clinina.vet.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoCalendarioDTO(
    Long id,
    Long animalId,
    String animalNome,
    String tutorNome,
    String sexo,
    String especie,
    String raca,
    String tamanho,
    String pelagem,
    String foto,
    LocalDate data,
    LocalTime horaEntrada,
    LocalTime horaSaida,
    String status,
    String tipo,
    String descricao,
    String observacoes,
    List<ServicoDTO> servicos
) {
}
