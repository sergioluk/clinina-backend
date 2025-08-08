package clinina.vet.api.controller;

import clinina.vet.api.dto.ServicoDTO;
import clinina.vet.api.mapper.ServicoMapper;
import clinina.vet.api.model.Servico;
import clinina.vet.api.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banho-e-tosa/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;
    @Autowired
    private ServicoMapper servicoMapper;

    @PostMapping
    public ServicoDTO criar(@RequestBody ServicoDTO dto) {
        Servico servico = servicoMapper.toEntity(dto);
        Servico salvo = servicoService.salvar(servico);
        return servicoMapper.toDTO(salvo);
    }

    @GetMapping
    public List<ServicoDTO> listar() {
        return servicoService.listar().stream().map(servicoMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ServicoDTO buscarPorId(@PathVariable Long id) {
        return servicoMapper.toDTO(servicoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        servicoService.excluir(id);
    }

    @PutMapping("/{id}")
    public ServicoDTO atualizar(@PathVariable Long id, @RequestBody ServicoDTO dto) {
        Servico atualizado = servicoService.atualizar(id, dto);
        return servicoMapper.toDTO(atualizado);
    }
}
