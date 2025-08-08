package clinina.vet.api.mapper;

import clinina.vet.api.dto.ClienteDTO;
import clinina.vet.api.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    private final PessoaMapper pessoaMapper;

    public ClienteMapper(PessoaMapper pessoaMapper) {
        this.pessoaMapper = pessoaMapper;
    }

    public ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                pessoaMapper.toDTO(cliente.getPessoa())
        );
    }

    public Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
                pessoaMapper.toEntity(dto.pessoa())
        );
    }
}
