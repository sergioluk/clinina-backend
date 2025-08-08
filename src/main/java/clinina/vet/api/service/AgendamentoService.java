package clinina.vet.api.service;

import clinina.vet.api.dto.AgendamentoCalendarioDTO;
import clinina.vet.api.dto.AgendamentoDTO;
import clinina.vet.api.dto.ServicoDTO;
import clinina.vet.api.model.Agendamento;
import clinina.vet.api.model.Animal;
import clinina.vet.api.model.Servico;
import clinina.vet.api.model.enums.StatusAgendamento;
import clinina.vet.api.model.enums.TipoAgendamento;
import clinina.vet.api.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listar() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com id: " + id));
    }

    public void excluir(Long id) {
        Agendamento agendamento = buscarPorId(id);
        agendamentoRepository.delete(agendamento);
    }

    @Transactional
    public Agendamento atualizar(Long id, AgendamentoDTO dto) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com id " + id));

        agendamentoExistente.setData(dto.data());
        agendamentoExistente.setHoraEntrada(dto.horaEntrada());
        agendamentoExistente.setHoraSaida(dto.horaSaida());
        agendamentoExistente.setTipo(TipoAgendamento.valueOf(dto.tipo()));
        agendamentoExistente.setStatus(StatusAgendamento.valueOf(dto.status()));
        agendamentoExistente.setDescricao(dto.descricao());
        agendamentoExistente.setObservacoes(dto.observacoes());

        // Atualizar relação com o animal
        Animal animal = new Animal();
        animal.setId(dto.animalId());
        agendamentoExistente.setAnimal(animal);

        // Atualizar serviços
        List<Servico> servicos = dto.servicosIds().stream().map(servicoId -> {
            Servico servico = new Servico();
            servico.setId(servicoId);
            return servico;
        }).collect(Collectors.toList());

        agendamentoExistente.setServicos(servicos);

        return agendamentoRepository.save(agendamentoExistente);
    }

    public List<AgendamentoCalendarioDTO> listarParaCalendario() {
        return agendamentoRepository.findAll().stream().map(agendamento -> {
            var animal = agendamento.getAnimal();
            var tutor = animal.getTutor();

            List<ServicoDTO> servicoDTOs = agendamento.getServicos().stream()
                    .map(s -> new ServicoDTO(s.getId(), s.getNome(), s.getDescricao(), s.getPreco(), s.getCategoria()))
                    .toList();

            return new AgendamentoCalendarioDTO(
                    agendamento.getId(),
                    animal.getId(),
                    animal.getNome(),
                    tutor != null ? tutor.getPessoa().getNome() : null,
                    animal.getSexo() != null ? animal.getSexo().name() : null,
                    animal.getEspecie() != null ? animal.getEspecie().name() : null,
                    animal.getRaca(),
                    animal.getTamanho() != null ? animal.getTamanho().name() : null,
                    animal.getPelagem(),
                    animal.getFoto(),
                    agendamento.getData(),
                    agendamento.getHoraEntrada(),
                    agendamento.getHoraSaida(),
                    agendamento.getStatus().name(),
                    agendamento.getTipo().name(),
                    agendamento.getDescricao(),
                    agendamento.getObservacoes(),
                    servicoDTOs
            );
        }).toList();
    }

}
