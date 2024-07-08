package clinina.vet.api.venda;

import java.util.Date;

public record LinhaDoTempoDTO(
        Long id,
        Double precoVenda,
        int quantidade,
        Date dataVenda
) {
    public LinhaDoTempoDTO(Venda v) {
        this(
                v.getId(),
                v.getPrecoUnitario() * v.getQuantidade(),
                v.getQuantidade(),
                v.getData()
        );
    }
}
