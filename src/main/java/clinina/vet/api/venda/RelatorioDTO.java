package clinina.vet.api.venda;

import java.util.List;

public record RelatorioDTO(
        List<Double> listaTotalVendasGrafico,
        List<DadosItensVendidos> relatorio
) {
}
