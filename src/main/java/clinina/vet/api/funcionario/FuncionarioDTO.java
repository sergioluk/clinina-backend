package clinina.vet.api.funcionario;

public record FuncionarioDTO(
        String usuario,
        String grupo
) {
    public FuncionarioDTO(String usuario, String grupo) {
        this.usuario = usuario;
        this.grupo = grupo;
    }
}
