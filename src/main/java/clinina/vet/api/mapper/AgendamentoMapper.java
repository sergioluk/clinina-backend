package clinina.vet.api.mapper;

import clinina.vet.api.dto.AgendamentoDTO;
import clinina.vet.api.model.Agendamento;
import clinina.vet.api.model.Animal;
import clinina.vet.api.model.Servico;
import clinina.vet.api.model.enums.StatusAgendamento;
import clinina.vet.api.model.enums.TipoAgendamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgendamentoMapper {

    public AgendamentoDTO toDTO(Agendamento agendamento) {
        List<Long> servicosIds = agendamento.getServicos().stream()
                .map(s -> s.getId())
                .collect(Collectors.toList());

        return new AgendamentoDTO(
                agendamento.getId(),
                agendamento.getAnimal().getId(),
                agendamento.getData(),
                agendamento.getHoraEntrada(),
                agendamento.getHoraSaida(),
                agendamento.getTipo().name(),
                agendamento.getStatus().name(),
                agendamento.getDescricao(),
                agendamento.getObservacoes(),
                servicosIds
        );
    }

    public Agendamento toEntity(AgendamentoDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.id());

        Animal animal = new Animal();
        animal.setId(dto.animalId());
        agendamento.setAnimal(animal);

        agendamento.setData(dto.data());
        agendamento.setHoraEntrada(dto.horaEntrada());
        agendamento.setHoraSaida(dto.horaSaida());
        agendamento.setTipo(TipoAgendamento.valueOf(dto.tipo()));
        agendamento.setStatus(StatusAgendamento.valueOf(dto.status()));
        agendamento.setDescricao(dto.descricao());
        agendamento.setObservacoes(dto.observacoes());

        if (dto.servicosIds() != null) {
            List<Servico> servicos = dto.servicosIds().stream().map(id -> {
                Servico servico = new Servico();
                servico.setId(id);
                return servico;
            }).toList();
            agendamento.setServicos(servicos);
        }

        return agendamento;
    }
}
