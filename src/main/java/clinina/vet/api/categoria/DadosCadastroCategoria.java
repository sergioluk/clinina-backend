package clinina.vet.api.categoria;

public record DadosCadastroCategoria(

        Long id,
        String nome
) {
    //listagem
    public DadosCadastroCategoria(Categoria categoria){
        this(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
