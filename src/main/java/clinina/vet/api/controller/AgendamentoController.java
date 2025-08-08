package clinina.vet.api.controller;

import clinina.vet.api.dto.AgendamentoCalendarioDTO;
import clinina.vet.api.dto.AgendamentoDTO;
import clinina.vet.api.mapper.AgendamentoMapper;
import clinina.vet.api.model.Agendamento;
import clinina.vet.api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banho-e-tosa/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoMapper agendamentoMapper;

    @PostMapping
    public AgendamentoDTO criar (@RequestBody AgendamentoDTO dto) {
        Agendamento agendamento = agendamentoMapper.toEntity(dto);
        Agendamento salvo = agendamentoService.salvar(agendamento);
        return agendamentoMapper.toDTO(salvo);
    }

    @GetMapping
    public List<AgendamentoDTO> listar() {
        return agendamentoService.listar().stream().map(agendamentoMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public AgendamentoDTO buscarPorId(@PathVariable Long id) {
        return agendamentoMapper.toDTO(agendamentoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        agendamentoService.excluir(id);
    }

    @PutMapping("/{id}")
    public AgendamentoDTO atualizar(@PathVariable Long id, @RequestBody AgendamentoDTO dto) {
        Agendamento atualizado = agendamentoService.atualizar(id, dto);
        return agendamentoMapper.toDTO(atualizado);
    }

    @GetMapping("/calendario")
    public List<AgendamentoCalendarioDTO> listarParaCalendario() {
        return agendamentoService.listarParaCalendario();
    }


}
