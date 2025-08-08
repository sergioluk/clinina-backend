package clinina.vet.api.dto;

public record PessoaDTO(
        String nome,
        String cpf,
        String telefone,
        String celular,
        String email,
        EnderecoDTO endereco
) {
}
