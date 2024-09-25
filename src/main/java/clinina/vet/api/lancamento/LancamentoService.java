package clinina.vet.api.lancamento;

import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaRepository;
import clinina.vet.api.lancamento.lancamentosdto.LancamentosDTO;
import clinina.vet.api.lancamento.lancamentosdto.ListaLancamentosDTO;
import clinina.vet.api.lancamento.lancamentosdto.PaginaLancamentosDTO;
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
    public List<CadastroLancamentoDTO> cadastrar (CadastroLancamentoDTO lancamentoDTO) {
        List<CadastroLancamentoDTO> listaRetornada = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lancamentoDTO.dataDaReceitaVencimento());

        for (int i = 0; i < lancamentoDTO.quantidadeParcelas(); i++) {

            Lancamento lancamento = new Lancamento(lancamentoDTO);

            lancamento.setValor(lancamento.getValor() / lancamento.getQuantidadeParcelas());

            if ( i > 0 ) {
                calendar.add(Calendar.MONTH, 1);// Incrementa 1 mês a cada parcela
                if (lancamento.getQuantidadeParcelas() > 0 ) {
                    lancamento.setDataDaReceitaVencimento(null);
                }
            }
            lancamento.setDataDaReceitaVencimento(calendar.getTime());
            Lancamento lancamentoCriado = this.lancamentoRepository.save(lancamento);
            listaRetornada.add(new CadastroLancamentoDTO(lancamentoCriado));
        }

        return listaRetornada;
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

    public PaginaLancamentosDTO getLancamentos(String dataInicio, String dataFim) {

        LocalDate localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int inicioDia = localDateInicio.getDayOfMonth();
        int inicioMes = localDateInicio.getMonthValue();
        int inicioAno = localDateInicio.getYear();
        LocalDate localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int fimDia = localDateFim.getDayOfMonth();
        int fimMes = localDateFim.getMonthValue();
        int fimAno = localDateFim.getYear();

        CategoriasDTO categorias = this.getCategorias();

        /**/
        double projecaoSaldo = 0;
        double saldoAtual = 0;
        double saldoAnterior = 0;
        List<Object[]>  somaReceitasDespesas = this.lancamentoRepository.getLancamentoSums(localDateInicio, localDateFim);
        if (somaReceitasDespesas != null && !somaReceitasDespesas.isEmpty()) {
            Object[] row = somaReceitasDespesas.get(0);
            if (row[0] != null && row[1] != null && row[2] != null && row[3] != null && row[4] != null && row[5] != null && row[6] != null && row[7] != null) {
                Double totalDespesas = ((Number) row[0]).doubleValue();
                Double totalReceitas = ((Number) row[1]).doubleValue();
                Double totalDespesasPeriodo = ((Number) row[2]).doubleValue();
                Double totalReceitasPeriodo = ((Number) row[3]).doubleValue();
                Double totalDespesasPeriodoAnterior = ((Number) row[4]).doubleValue();
                Double totalReceitasPeriodoAnterior = ((Number) row[5]).doubleValue();
                Double totalDespesasSaldoAtual = ((Number) row[6]).doubleValue();
                Double totalReceitasSaldoAtual = ((Number) row[7]).doubleValue();

                projecaoSaldo = totalReceitas - totalDespesas;
                saldoAtual = totalReceitasSaldoAtual - totalDespesasSaldoAtual;
                saldoAnterior = totalReceitasPeriodoAnterior - totalDespesasPeriodoAnterior;

                System.out.println("Total Despesas: " + totalDespesas);
                System.out.println("Total Receitas: " + totalReceitas);
                System.out.println("Total Despesas Periodo da data " + localDateInicio.toString() + " e " + localDateFim.toString() + ": " + totalDespesasPeriodo);
                System.out.println("Total Receitas Periodo da data " + localDateInicio.toString() + " e " + localDateFim.toString() + ": " + totalReceitasPeriodo);
            }
        }

        /**/

        List<LancamentosDTO> lancamentos = this.lancamentoRepository.getLancamentosPorData(inicioDia,inicioMes,inicioAno,fimDia,fimMes,fimAno)
                .stream().map(l -> new LancamentosDTO(
                        l.getId(),
                        l.getDataDaReceitaVencimento(),
                        l.getDataRecebimentoPagamento(),
                        getStatus(l),
                        l.getDescricao(),
                        getNomeCategoria(l.getCategoriaId(), l.getTipoReceita(), categorias),
                        l.getValor(),
                        l.getTipoReceita()
                ))
                .toList();


        // Criar uma lista mutável a partir de uma lista imutável
        List<LancamentosDTO> lancamentosMutavel = new ArrayList<>(lancamentos);

        // Ordenar os lançamentos por data de forma decrescente
        //lancamentosMutavel.sort((l1, l2) -> l2.dataDaReceitaVencimento().compareTo(l1.dataDaReceitaVencimento()));
        // Ordenar os lançamentos por data de pagamento ou data de vencimento de forma decrescente
        lancamentosMutavel.sort((l1, l2) -> {
            // Usar dataRecebimentoPagamento se disponível, senão usar dataDaReceitaVencimento
            Date dataL1 = l1.dataRecebimentoPagamento() != null ? l1.dataRecebimentoPagamento() : l1.dataDaReceitaVencimento();
            Date dataL2 = l2.dataRecebimentoPagamento() != null ? l2.dataRecebimentoPagamento() : l2.dataDaReceitaVencimento();

            // Comparar as datas de forma decrescente
            return dataL2.compareTo(dataL1);
        });

        List<ListaLancamentosDTO> listaLancamentos = new ArrayList<>();
        Map<String, ListaLancamentosDTO> agrupamentoPorData = new LinkedHashMap<>();

//        for (LancamentosDTO lancamento : lancamentosMutavel) {
//            Date data = lancamento.dataDaReceitaVencimento();
//            String chaveData = String.format("%d-%02d-%02d", data.getYear(), data.getMonth(), data.getDate());
//
//            if (!agrupamentoPorData.containsKey(chaveData)) {
//                // Se não existe um agrupamento para essa data, cria um novo
//                agrupamentoPorData.put(chaveData, new ListaLancamentosDTO(data, new ArrayList<>()));
//            }
//
//            // Adiciona o lançamento ao grupo da data correspondente
//            agrupamentoPorData.get(chaveData).lancamentos().add(lancamento);
//        }
        for (LancamentosDTO lancamento : lancamentosMutavel) {
            // Verificar se o lançamento tem a dataRecebimentoPagamento (ou seja, está pago)
            Date dataAgrupamento = lancamento.dataRecebimentoPagamento() != null
                    ? lancamento.dataRecebimentoPagamento()  // Se pago, usa a data de recebimento
                    : lancamento.dataDaReceitaVencimento();  // Caso contrário, usa a data de vencimento

            // Formatar a data de agrupamento no formato desejado
            String chaveData = String.format("%d-%02d-%02d", dataAgrupamento.getYear(), dataAgrupamento.getMonth(), dataAgrupamento.getDate());

            if (!agrupamentoPorData.containsKey(chaveData)) {
                // Se não existe um agrupamento para essa data, cria um novo
                agrupamentoPorData.put(chaveData, new ListaLancamentosDTO(dataAgrupamento, new ArrayList<>()));
            }

            // Adiciona o lançamento ao grupo da data correspondente
            agrupamentoPorData.get(chaveData).lancamentos().add(lancamento);
        }

        // Adicionar os grupos ao resultado final
        listaLancamentos.addAll(agrupamentoPorData.values());

        // Total de vendas do dia
        double total = this.vendaRepository.totalVendasLancamento(inicioDia, inicioMes, inicioAno, fimDia, fimMes, fimAno);
        LancamentosDTO vendasDoDia = new LancamentosDTO(0L, new Date(), new Date(),"Pago", "Vendas diárias", "Vendas diárias", total, "receita");
        ListaLancamentosDTO vendas = new ListaLancamentosDTO(new Date(), new ArrayList<>());
        vendas.lancamentos().add(vendasDoDia);

        //listaLancamentos.add(0, vendas);

        PaginaLancamentosDTO paginaLancamento = new PaginaLancamentosDTO(0,0,projecaoSaldo, saldoAnterior, saldoAtual, listaLancamentos);

        return paginaLancamento;
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
