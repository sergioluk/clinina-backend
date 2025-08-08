package clinina.vet.api.service;

import clinina.vet.api.dto.ClienteDTO;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.model.Cliente;
import clinina.vet.api.model.Pessoa;
import clinina.vet.api.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    public void excluir(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }

    @Transactional
    public Cliente atualizar(Long id, ClienteDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id " + id));

        Pessoa pessoa = clienteExistente.getPessoa();

        pessoa.setNome(dto.pessoa().nome());
        pessoa.setCpf(dto.pessoa().cpf());
        pessoa.setTelefone(dto.pessoa().telefone());
        pessoa.setCelular(dto.pessoa().celular());
        pessoa.setEmail(dto.pessoa().email());

        Endereco endereco = pessoa.getEndereco();
        if (endereco == null) {
            endereco = new Endereco();
            pessoa.setEndereco(endereco);
        }

        endereco.setRua(dto.pessoa().endereco().rua());
        endereco.setNumero(dto.pessoa().endereco().numero());
        endereco.setBairro(dto.pessoa().endereco().bairro());
        endereco.setCidade(dto.pessoa().endereco().cidade());
        endereco.setUf(dto.pessoa().endereco().uf());
        endereco.setCep(dto.pessoa().endereco().cep());
        endereco.setComplemento(dto.pessoa().endereco().complemento());

        return clienteRepository.save(clienteExistente);
    }
}
