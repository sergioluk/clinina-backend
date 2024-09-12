package clinina.vet.api.lancamento;

import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaRepository;
import clinina.vet.api.lancamento.lancamentosdto.LancamentosDTO;
import clinina.vet.api.lancamento.lancamentosdto.ListaLancamentosDTO;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaRepository;
import clinina.vet.api.venda.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

//    public List<ListaLancamentosDTO> getLancamentos() {
//
//        CategoriasDTO categorias = this.getCategorias();
//
//        List<LancamentosDTO> lancamentos = this.lancamentoRepository.findAll().stream()
//                .map(l -> new LancamentosDTO(
//                        l.getId(),
//                        l.getDataDaReceitaVencimento(),
//                        getStatus(l),
//                        l.getDescricao(),
//                        getNomeCategoria(l.getCategoriaId(),l.getTipoReceita(),categorias),
//                        l.getValor(),
//                        l.getTipoReceita()
//                ))
//                .toList();
//
//        //Criar uma lista mutavel a partir de uma lista imutavel
//        List<LancamentosDTO> lancamentosMutavel = new ArrayList<>(lancamentos);
//
//        for (LancamentosDTO l : lancamentosMutavel) {
//            System.out.println(l.descricao() + " " + l.dataDaReceitaVencimento());
//        }
//
//        lancamentosMutavel.sort((l1, l2) -> l2.dataDaReceitaVencimento().compareTo(l1.dataDaReceitaVencimento()));
//
//        List<ListaLancamentosDTO> listaLancamentos = new ArrayList<>();
//
//        Date data = null;
//        ListaLancamentosDTO objLancamento = null;
//
//        for (int i = 0; i < lancamentosMutavel.size(); i++) {
//            if (i == 0) {
//                data = lancamentosMutavel.get(i).dataDaReceitaVencimento();
//                objLancamento = new ListaLancamentosDTO(data, new ArrayList<LancamentosDTO>());
//            }
//            if (data.getMonth() == lancamentosMutavel.get(i).dataDaReceitaVencimento().getMonth()) {
//                if (data.getDate() == lancamentosMutavel.get(i).dataDaReceitaVencimento().getDate() ) {
//                    objLancamento.lancamentos().add(lancamentosMutavel.get(i));
//                } else {
//                    data = lancamentosMutavel.get(i).dataDaReceitaVencimento();
//                    listaLancamentos.add(objLancamento);
//                    objLancamento = new ListaLancamentosDTO(data, new ArrayList<LancamentosDTO>());
//                    objLancamento.lancamentos().add(lancamentosMutavel.get(i));
//                }
//            } else {
//                data = lancamentosMutavel.get(i).dataDaReceitaVencimento();
//                objLancamento = new ListaLancamentosDTO(data, new ArrayList<LancamentosDTO>());
//                objLancamento.lancamentos().add(lancamentosMutavel.get(i));
//                listaLancamentos.add(objLancamento);
//            }
//
//        }
//
//        double total = this.vendaRepository.totalVendasLancamento(8,9,2024,8,9,2024);
//        LancamentosDTO vendasDoDia = new LancamentosDTO(0L, new Date(), "Pago", "Vendas diárias", "Vendas diárias", total, "receita");
//
//        lancamentosMutavel.add(vendasDoDia);
//
//        return listaLancamentos;
//    }

    public List<ListaLancamentosDTO> getLancamentos(String dataInicio, String dataFim) {

        LocalDate localDate = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int inicioDia = localDate.getDayOfMonth();
        int inicioMes = localDate.getMonthValue();
        int inicioAno = localDate.getYear();
        localDate = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int fimDia = localDate.getDayOfMonth();
        int fimMes = localDate.getMonthValue();
        int fimAno = localDate.getYear();

        CategoriasDTO categorias = this.getCategorias();

        List<LancamentosDTO> lancamentos = this.lancamentoRepository.getLancamentosPorData(inicioDia,inicioMes,inicioAno,fimDia,fimMes,fimAno)
                .stream().map(l -> new LancamentosDTO(
                        l.getId(),
                        l.getDataDaReceitaVencimento(),
                        getStatus(l),
                        l.getDescricao(),
                        getNomeCategoria(l.getCategoriaId(), l.getTipoReceita(), categorias),
                        l.getValor(),
                        l.getTipoReceita()
                ))
                .toList();
//        List<LancamentosDTO> lancamentos = this.lancamentoRepository.findAll().stream()
//                .map(l -> new LancamentosDTO(
//                        l.getId(),
//                        l.getDataDaReceitaVencimento(),
//                        getStatus(l),
//                        l.getDescricao(),
//                        getNomeCategoria(l.getCategoriaId(), l.getTipoReceita(), categorias),
//                        l.getValor(),
//                        l.getTipoReceita()
//                ))
//                .toList();

        // Criar uma lista mutável a partir de uma lista imutável
        List<LancamentosDTO> lancamentosMutavel = new ArrayList<>(lancamentos);

        // Ordenar os lançamentos por data de forma decrescente
        lancamentosMutavel.sort((l1, l2) -> l2.dataDaReceitaVencimento().compareTo(l1.dataDaReceitaVencimento()));

        List<ListaLancamentosDTO> listaLancamentos = new ArrayList<>();
        Map<String, ListaLancamentosDTO> agrupamentoPorData = new LinkedHashMap<>();

        for (LancamentosDTO lancamento : lancamentosMutavel) {
            Date data = lancamento.dataDaReceitaVencimento();
            String chaveData = String.format("%d-%02d-%02d", data.getYear(), data.getMonth(), data.getDate());

            if (!agrupamentoPorData.containsKey(chaveData)) {
                // Se não existe um agrupamento para essa data, cria um novo
                agrupamentoPorData.put(chaveData, new ListaLancamentosDTO(data, new ArrayList<>()));
            }

            // Adiciona o lançamento ao grupo da data correspondente
            agrupamentoPorData.get(chaveData).lancamentos().add(lancamento);
        }

        // Adicionar os grupos ao resultado final
        listaLancamentos.addAll(agrupamentoPorData.values());

        // Total de vendas do dia
        double total = this.vendaRepository.totalVendasLancamento(inicioDia, inicioMes, inicioAno, fimDia, fimMes, fimAno);
        LancamentosDTO vendasDoDia = new LancamentosDTO(0L, new Date(), "Pago", "Vendas diárias", "Vendas diárias", total, "receita");
        ListaLancamentosDTO vendas = new ListaLancamentosDTO(new Date(), new ArrayList<>());
        vendas.lancamentos().add(vendasDoDia);

        listaLancamentos.add(0, vendas);

        return listaLancamentos;
    }


    private String getStatus(Lancamento l) {
        String status = "";
        LocalDate dataVencimento = l.getDataDaReceitaVencimento().toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDate dataHoje = LocalDate.now(ZoneId.of("UTC"));

        long diferencaDias = ChronoUnit.DAYS.between(dataHoje, dataVencimento);

        if (l.getDataRecebimentoPagamento() != null) {
            status = "Pago";
        } else if (diferencaDias == 0) {
            status = "Vence hoje";
        } else if (diferencaDias < 0) {
            status = "Vencido";
        } else {
            status = "A vencer";
        }

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
