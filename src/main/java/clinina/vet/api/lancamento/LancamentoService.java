package clinina.vet.api.lancamento;

import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaRepository;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaRepository;
import clinina.vet.api.venda.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private ReceitaCategoriaRepository receitaCategoriaRepository;

    @Autowired
    private DespesaCategoriaRepository despesaCategoriaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    public CategoriasDTO getCategorias() {
        List<ReceitaCategoriaDTO> receitas = this.receitaCategoriaRepository.findAll().stream()
                .map(receita -> new ReceitaCategoriaDTO(receita.getId(), receita.getNome()))
                .toList();
        List<DespesaCategoriaDTO> despesas = this.despesaCategoriaRepository.findAll().stream()
                .map(despesa -> new DespesaCategoriaDTO(despesa.getId(), despesa.getNome()))
                .toList();

        return new CategoriasDTO(receitas, despesas);
    }

    @Transactional
    public CadastroLancamentoDTO cadastrar (CadastroLancamentoDTO lancamentoDTO) {
        Lancamento lancamento = new Lancamento(lancamentoDTO);
        Lancamento lancamentoCriado = this.lancamentoRepository.save(lancamento);
        return new CadastroLancamentoDTO(lancamentoCriado);
    }

    public List<LancamentosDTO> getLancamentos() {

        CategoriasDTO categorias = this.getCategorias();

        List<LancamentosDTO> lancamentos = this.lancamentoRepository.findAll().stream()
                .map(l -> new LancamentosDTO(
                        l.getId(),
                        l.getDataDaReceitaVencimento(),
                        getStatus(l),
                        l.getDescricao(),
                        getNomeCategoria(l.getCategoriaId(),l.getTipoReceita(),categorias),
                        l.getValor(),
                        l.getTipoReceita()
                ))
                .toList();

        //Criar uma lista mutavel a partir de uma lista imutavel
        List<LancamentosDTO> lancamentosMutavel = new ArrayList<>(lancamentos);

        double total = this.vendaRepository.totalVendasLancamento(8,9,2024,8,9,2024);
        LancamentosDTO vendasDoDia = new LancamentosDTO(0L, new Date(), "Pago", "Vendas diárias", "Vendas diárias", total, "receita");

        lancamentosMutavel.add(vendasDoDia);

        return lancamentosMutavel;
    }

    private String getStatus(Lancamento l) {
        String status = "";
        LocalDate dataVencimento = l.getDataDaReceitaVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dataHoje = LocalDate.now();

        long diferencaDias = ChronoUnit.DAYS.between(dataHoje, dataVencimento);
        long diasParaAVencer = 5;

        if (l.getDataRecebimentoPagamento() != null) {
            status = "Pago";
        } else if (diferencaDias == 0) {
            status = "Vence hoje";
        } else if (diferencaDias > 0 && diferencaDias <= diasParaAVencer) {
            status = "A vencer";
        } else if (diferencaDias < 0) {
            status = "Vencido";
        }

        System.out.println("Data vencimento: " + dataVencimento);
        System.out.println("Data Hoje: " + dataHoje);
        System.out.println("Diferenca entre dias: " + diferencaDias);
        System.out.println("---------------------");

        return status;
    }

    private String getNomeCategoria(long id, String tipoReceita, CategoriasDTO categorias) {
        String nomeCategoria = "";
        if (tipoReceita.equals("receita")) {
            for (ReceitaCategoriaDTO r : categorias.receitas()) {
                if (id == r.id()) {
                    nomeCategoria = r.nome();
                }
            }
        }
        if (tipoReceita.equals("despesa")) {
            for (DespesaCategoriaDTO d : categorias.despesas()) {
                if (id == d.id()) {
                    nomeCategoria = d.nome();
                }
            }
        }
        return nomeCategoria;
    }
}
