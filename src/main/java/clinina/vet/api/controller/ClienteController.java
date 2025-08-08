package clinina.vet.api.controller;

import clinina.vet.api.dto.ClienteDTO;
import clinina.vet.api.mapper.ClienteMapper;
import clinina.vet.api.model.Cliente;
import clinina.vet.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banho-e-tosa/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteMapper clienteMapper;

    @PostMapping
    public ClienteDTO criar(@RequestBody ClienteDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente salvo = clienteService.salvar(cliente);
        return clienteMapper.toDTO(salvo);
    }

    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteService.listar().stream().map(clienteMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        return clienteMapper.toDTO(clienteService.buscarPorId(id)) ;
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        clienteService.excluir(id);
    }

    @PutMapping("/{id}")
    public ClienteDTO atualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        Cliente atualizado = clienteService.atualizar(id, dto);
        return clienteMapper.toDTO(atualizado);
    }
}
