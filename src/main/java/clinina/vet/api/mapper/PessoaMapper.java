package clinina.vet.api.mapper;

import clinina.vet.api.dto.EnderecoDTO;
import clinina.vet.api.dto.PessoaDTO;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.model.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

    public PessoaDTO toDTO(Pessoa pessoa) {
        Endereco endereco = pessoa.getEndereco();
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep(),
                endereco.getComplemento()
        );

        return new PessoaDTO(
                pessoa.getNome(),
                pessoa.getCpf(),
                pessoa.getTelefone(),
                pessoa.getCelular(),
                pessoa.getEmail(),
                enderecoDTO
        );
    }

    public Pessoa toEntity(PessoaDTO dto) {
        Endereco endereco = new Endereco(
                dto.endereco().rua(),
                dto.endereco().numero(),
                dto.endereco().bairro(),
                dto.endereco().cidade(),
                dto.endereco().uf(),
                dto.endereco().cep(),
                dto.endereco().complemento()
        );

        return new Pessoa(
                dto.nome(),
                dto.cpf(),
                dto.telefone(),
                dto.celular(),
                dto.email(),
                endereco
        );
    }
}
