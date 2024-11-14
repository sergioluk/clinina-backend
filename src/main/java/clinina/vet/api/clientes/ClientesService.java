package clinina.vet.api.clientes;

import clinina.vet.api.categoria.Categoria;
import clinina.vet.api.categoria.DadosCadastroCategoria;
import clinina.vet.api.tutor.TutorDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    @Transactional
    public ClientesDTO salvarCliente(ClientesDTO dados) {
        Clientes cliente = new Clientes();
        cliente.setNome(dados.nome());
        cliente.setTelefone(dados.telefone());
        Clientes clienteCriado = this.clientesRepository.save(cliente);
        return new ClientesDTO(clienteCriado);
    }

    public List<ClientesDTO> listarClientes() {
        return this.clientesRepository.findAll().stream().map(
                c -> new ClientesDTO(c.getId(), c.getNome(),c.getTelefone())
        ).collect(Collectors.toList());
    }
}
