package clinina.vet.api.dto;

public record EnderecoDTO(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String cep,
        String complemento
) {

}
