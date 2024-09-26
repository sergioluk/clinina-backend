package clinina.vet.api.lancamento.lancamentosdto;

import java.util.List;

public class ListasPorcentagemCategoriasDTO {
    private List<CategoriaPorcentagemDTO> receitas;
    private List<CategoriaPorcentagemDTO> despesas;

    public ListasPorcentagemCategoriasDTO(List<CategoriaPorcentagemDTO> receitas, List<CategoriaPorcentagemDTO> despesas) {
        this.receitas = receitas;
        this.despesas = despesas;
    }

    public List<CategoriaPorcentagemDTO> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<CategoriaPorcentagemDTO> receitas) {
        this.receitas = receitas;
    }

    public List<CategoriaPorcentagemDTO> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<CategoriaPorcentagemDTO> despesas) {
        this.despesas = despesas;
    }
}
