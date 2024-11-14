package clinina.vet.api.controller;

import clinina.vet.api.clientes.ClientesDTO;
import clinina.vet.api.clientes.ClientesRepository;
import clinina.vet.api.clientes.ClientesService;
import clinina.vet.api.lancamento.CadastroLancamentoDTO;
import clinina.vet.api.lancamento.LancamentoService;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import clinina.vet.api.tutor.TutorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @PostMapping
    public ResponseEntity<ClientesDTO> salvar(@RequestBody ClientesDTO clientesDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientesService.salvarCliente(clientesDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClientesDTO>> listarClientes() {
        return ResponseEntity.ok(this.clientesService.listarClientes());
    }

}
