package clinina.vet.api.fornecedor;

import clinina.vet.api.Idade.Idade;

public record DadosCadastroFornecedor(
        Long id,
        String nome
) {
    public DadosCadastroFornecedor(Fornecedor fornecedor){
        this(
                fornecedor.getId(),
                fornecedor.getNome()
        );
    }
}
