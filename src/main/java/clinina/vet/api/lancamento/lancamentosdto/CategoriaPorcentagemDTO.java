package clinina.vet.api.lancamento.lancamentosdto;

import lombok.Getter;
import lombok.Setter;

public class CategoriaPorcentagemDTO {
    @Getter
    @Setter
    private String categoria;
    @Getter
    @Setter
    private double porcentagem;

    public CategoriaPorcentagemDTO(String categoria, double porcentagem) {
        this.categoria = categoria;
        this.porcentagem = porcentagem;
    }

}
