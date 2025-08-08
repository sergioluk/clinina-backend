package clinina.vet.api.mapper;

import clinina.vet.api.dto.ServicoDTO;
import clinina.vet.api.model.Servico;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper {

    public ServicoDTO toDTO(Servico servico) {
        return new ServicoDTO(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getPreco(),
                servico.getCategoria()
        );
    }

    public Servico toEntity(ServicoDTO dto) {
        Servico servico = new Servico();
        servico.setId(dto.id());
        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setPreco(dto.preco());
        servico.setCategoria(dto.categoria());
        return servico;
    }
}
