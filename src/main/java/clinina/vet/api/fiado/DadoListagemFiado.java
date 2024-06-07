package clinina.vet.api.fiado;

import clinina.vet.api.venda.DadosItensVendidos;

import java.util.Date;
import java.util.List;

public record DadoListagemFiado(
    Long id,
    String nome,
    String telefone,
    String endereco,
    Date data,
    Double valorTotal,
    List<DadosItensVendidos> itens,
    int pagou,
    Date modified_at,
    double pagamento
) {
}
