package clinina.vet.api.clientes;

public record ClientesDTO(
        Long id,
        String nome,
        String telefone
) {
    public ClientesDTO(Clientes clienteCriado) {
        this (
                clienteCriado.getId(),
                clienteCriado.getNome(),
                clienteCriado.getTelefone()
        );
    }
}
