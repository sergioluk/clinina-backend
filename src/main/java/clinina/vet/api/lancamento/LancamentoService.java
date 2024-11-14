package clinina.vet.api.lancamento;

import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaRepository;
import clinina.vet.api.lancamento.lancamentosdto.*;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaRepository;
import clinina.vet.api.venda.VendaRepository;
import clinina.vet.api.venda.VendasDataTotalDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        // Total de vendas a partir de outubro, se colocar para alugar, setar pra false
        boolean alugar = true;
        String dataInicial = "2024-01-01";
        if (alugar) {
            dataInicial = "2024-10-01";
        }
        Double totalVendasDiarias = 0d;
        Double totalVendasDiariasAteData = 0d;
        Double totalReceitasPeriodo = 0d;
        Double totalDespesasPeriodo = 0d;
        Double somaTotalVendasPeriodo = 0d;
        List<Object[]> somaTotalVendasTotalVendasMenorQueHoje = this.vendaRepository.somaTotalVendasTotalVendasMenorQueHoje(dataInicial, localDateFim);
        if (somaTotalVendasTotalVendasMenorQueHoje != null && !somaTotalVendasTotalVendasMenorQueHoje.isEmpty()){
            Object[] row = somaTotalVendasTotalVendasMenorQueHoje.get(0);
            if (row[0] != null && row[1] != null && row[2] != null) {
                totalVendasDiarias = ((Number) row[0]).doubleValue();
                totalVendasDiariasAteData = ((Number) row[1]).doubleValue();
                somaTotalVendasPeriodo = ((Number) row[2]).doubleValue();
            }
        }

        double projecaoSaldo = 0;
        double saldoAtual = 0;
        double saldoAnterior = 0;
        double aPagar = 0;
        double aReceber = 0;
        List<Object[]>  somaReceitasDespesas = this.lancamentoRepository.getLancamentoSums(localDateInicio, localDateFim);
        if (somaReceitasDespesas != null && !somaReceitasDespesas.isEmpty()) {
            Object[] row = somaReceitasDespesas.get(0);
            if (row[0] != null && row[1] != null && row[2] != null && row[3] != null && row[4] != null
                    && row[5] != null && row[6] != null && row[7] != null && row[8] != null && row[9] != null) {
                Double totalDespesas = ((Number) row[0]).doubleValue();
                Double totalReceitas = ((Number) row[1]).doubleValue();
                Double totalDespesasPeriodoAnterior = ((Number) row[2]).doubleValue();
                Double totalReceitasPeriodoAnterior = ((Number) row[3]).doubleValue();
                Double totalDespesasSaldoAtual = ((Number) row[4]).doubleValue();
                Double totalReceitasSaldoAtual = ((Number) row[5]).doubleValue();
                Double totalDespesasAPagar = ((Number) row[6]).doubleValue();
                Double totalReceitasSAReceber = ((Number) row[7]).doubleValue();
                totalReceitasPeriodo = ((Number) row[8]).doubleValue();
                totalDespesasPeriodo = ((Number) row[9]).doubleValue();

                projecaoSaldo = totalReceitas + totalVendasDiarias - totalDespesas;
                saldoAtual = totalReceitasSaldoAtual + totalVendasDiariasAteData - totalDespesasSaldoAtual;
                saldoAnterior = totalReceitasPeriodoAnterior - totalDespesasPeriodoAnterior;
                aPagar = totalDespesasAPagar;
                aReceber = totalReceitasSAReceber;
            }
        }

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

        ListasPorcentagemCategoriasDTO listasPorcentagemCategorias = new ListasPorcentagemCategoriasDTO(null, null);


        //Map para contar quantos lançamentos tem por cada categoria
        Map<String, Integer> contagemPorCategoriaReceita = new HashMap<>();
        Map<String, Integer> contagemPorCategoriaDespesa = new HashMap<>();



        int qtdLancReceita = 0;
        int qtdLancDespesa = 0;
        for (LancamentosDTO lancamento : lancamentosMutavel) {

            //Contar quantos lancamentos tem por categoria
            String categoria = lancamento.categoriaNome();
            if (lancamento.tipoReceita().equals("receita")) {
                contagemPorCategoriaReceita.put(categoria, contagemPorCategoriaReceita.getOrDefault(categoria, 0) + 1);
                qtdLancReceita++;
            } else if (lancamento.tipoReceita().equals("despesa")) {
                contagemPorCategoriaDespesa.put(categoria, contagemPorCategoriaDespesa.getOrDefault(categoria, 0) + 1);
                qtdLancDespesa++;
            }


            // Verificar se o lançamento tem a dataRecebimentoPagamento (ou seja, está pago)
            Date dataAgrupamento = lancamento.dataRecebimentoPagamento() != null
                    ? lancamento.dataRecebimentoPagamento()  // Se pago, usa a data de recebimento
                    : lancamento.dataDaReceitaVencimento();  // Caso contrário, usa a data de vencimento

            // Formatar a data de agrupamento no formato desejado
            String chaveData = String.format("%d-%02d-%02d", dataAgrupamento.getYear(), dataAgrupamento.getMonth(), dataAgrupamento.getDate());

            if (!agrupamentoPorData.containsKey(chaveData)) {
                // Se não existir um agrupamento para essa data, criar um novo
                agrupamentoPorData.put(chaveData, new ListaLancamentosDTO(dataAgrupamento, new ArrayList<>()));
            }

            // Adiciona o lançamento ao grupo da data correspondente
            agrupamentoPorData.get(chaveData).lancamentos().add(lancamento);
        }


        List<CategoriaPorcentagemDTO> listaPorcentagemReceitas = new ArrayList<>();
        List<CategoriaPorcentagemDTO> listaPorcentagemDespesas = new ArrayList<>();
        //Calcular porcentagem das categorias
        for(Map.Entry<String, Integer> entry : contagemPorCategoriaReceita.entrySet()) {
            String categoria = entry.getKey();
            int quantidade = entry.getValue();
            double porcentagem = (quantidade / (double) qtdLancReceita) * 100;
            listaPorcentagemReceitas.add(new CategoriaPorcentagemDTO(categoria, porcentagem));
        }
        for(Map.Entry<String, Integer> entry : contagemPorCategoriaDespesa.entrySet()) {
            String categoria = entry.getKey();
            int quantidade = entry.getValue();
            double porcentagem = (quantidade / (double) qtdLancDespesa) * 100;
            listaPorcentagemDespesas.add(new CategoriaPorcentagemDTO(categoria, porcentagem));
        }

        listasPorcentagemCategorias.setReceitas(listaPorcentagemReceitas);
        listasPorcentagemCategorias.setDespesas(listaPorcentagemDespesas);

        // Adicionar os grupos ao resultado final
        listaLancamentos.addAll(agrupamentoPorData.values());

        // Total de vendas do dia
        //double total = this.vendaRepository.totalVendasLancamento(inicioDia, inicioMes, inicioAno, fimDia, fimMes, fimAno);
        //LancamentosDTO vendasDoDia = new LancamentosDTO(0L, new Date(), new Date(),"Pago", "Vendas diárias", "Vendas diárias", total, "receita");
        //ListaLancamentosDTO vendas = new ListaLancamentosDTO(new Date(), new ArrayList<>());
        //vendas.lancamentos().add(vendasDoDia);

        //listaLancamentos.add(0, vendas);

        /*vendas diarias*/
        List<VendasDataTotalDTO> vendasDataTotal = this.vendaRepository.vendasDataTotal(inicioDia, inicioMes, inicioAno, fimDia, fimMes, fimAno)
                .stream()
                .map(l -> new VendasDataTotalDTO((Date) l[0], (BigDecimal) l[1])) // Acesse os campos por índice
                .toList();

//        List<VendasDataTotalDTO> vendasDataTotal = new ArrayList<>(this.vendaRepository.vendasDataTotal(inicioDia, inicioMes, inicioAno, fimDia, fimMes, fimAno)
//                .stream().map(l -> new VendasDataTotalDTO(l.getData(), l.getPrecoTotal())
//                ).toList());
//        vendasDataTotal.sort((l1, l2) -> {
//            Date dataL1 = l1.data();
//            Date dataL2 = l2.data();
//            // Comparar as datas de forma decrescente
//            return dataL2.compareTo(dataL1);
//        });

        System.out.println("Antes Lista Lancamentos: ");
        for(ListaLancamentosDTO l : listaLancamentos) {
            System.out.println("Data: " + l.data());
            for(LancamentosDTO la: l.lancamentos()) {
                System.out.println("Id: " + la.id() + " dataDaReceitaVencimento: " + la.dataDaReceitaVencimento() + " dataRecebimentoPagamento: " + la.dataRecebimentoPagamento() + " Status: " + la.status() + " Descricao: " + la.descricao() + " Categoria: " + la.categoriaNome() + " Valor: " + la.valor() + " Tipo: " + la.tipoReceita());
            }
        }
        System.out.println("------------------");
        System.out.println("Vendas Data Total: ");
        for(VendasDataTotalDTO v: vendasDataTotal) {
            System.out.println("Data: " + v.data() + " Preço Total: " + v.precoTotal());
        }
        System.out.println("Tamanho da Lista Lancamento: " + listaLancamentos.size());
        System.out.println("Tamanho da Lista Vendas: " + vendasDataTotal.size());



        //List<ListaLancamentosDTO> lancamentosParaAdicionar = new ArrayList<>();
//        for(ListaLancamentosDTO l:listaLancamentos) {
//            LocalDate dataLancamento = l.data().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            for (VendasDataTotalDTO v:vendasDataTotal) {
//                LocalDate dataVenda = v.data().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//                if (dataVenda.equals(dataLancamento)) {
//                    LancamentosDTO vendaDesseDia = new LancamentosDTO(0L, v.data(), v.data(),"Pago", "Vendas diárias", "Vendas diárias", v.precoTotal().doubleValue(), "receita");
//                    l.lancamentos().add(0,vendaDesseDia);
//                } else {
//                    LancamentosDTO vendaDesseDia = new LancamentosDTO(0L, v.data(), v.data(),"Pago", "Vendas diárias", "Vendas diárias", v.precoTotal().doubleValue(), "receita");
//                    ListaLancamentosDTO vendasLista = new ListaLancamentosDTO(v.data(), new ArrayList<>());
//                    vendasLista.lancamentos().add(vendaDesseDia);
//                    //listaLancamentos.add(0, vendasLista);
//                    lancamentosParaAdicionar.add(vendasLista);
//                }
//            }
//        }

        List<ListaLancamentosDTO> novasLancamentos = new ArrayList<>();
        for(VendasDataTotalDTO v:vendasDataTotal) {
            //LocalDate dataVenda = v.data().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date dataVenda = v.data(); // Obtenha a data da venda
            LocalDate dataVendaSemHora = v.data().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LancamentosDTO vendaDesseDia = new LancamentosDTO(0L, dataVenda, dataVenda,"Pago", "Vendas diárias", "Vendas diárias", v.precoTotal().doubleValue(), "receita");

            // Verifique se já existe um ListaLancamentosDTO com a mesma data
            boolean encontrado = false;
            System.out.println("----------------");
            for (ListaLancamentosDTO l: listaLancamentos) {
                LocalDate dataLancamentoSemHora = l.data().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                System.out.println(dataLancamentoSemHora + "(" + l.data() + ")" + " : " + dataVendaSemHora + "(" + dataVenda + ")" + "? "  + dataLancamentoSemHora.equals(dataVendaSemHora));
                if (dataLancamentoSemHora.equals(dataVendaSemHora)) {
                    //Adicione o LancementosDTO ao lancamentos existente
                    l.lancamentos().add(0, vendaDesseDia);
                    encontrado = true;
                    System.out.println("Isso tem que aparecer uma vez só");
                    break; // Sai do loop já que encontramos a data
                }

                // Se não encontramos, criamos um novo ListaLancamentosDTO
            }
            if (!encontrado) {
//                    List<LancamentosDTO> lancamentoss = new ArrayList<>();
//                    lancamentos.add(vendaDesseDia);
//                    listaLancamentos.add(new ListaLancamentosDTO(dataVenda, lancamentoss));
                ListaLancamentosDTO vendasLista = new ListaLancamentosDTO(v.data(), new ArrayList<>());
                vendasLista.lancamentos().add(vendaDesseDia);
                //listaLancamentos.add(0, vendasLista);
                novasLancamentos.add(vendasLista);
            }
        }
        System.out.println("Isso vai aparecer");
        System.out.println("Tamanho da Lista Novos Lancamento: " + novasLancamentos.size());
        listaLancamentos.addAll(novasLancamentos);
        System.out.println("Tamanho da Lista Lancamento Final: " + listaLancamentos.size());



        //System.out.println("Depois: " + listaLancamentos.stream().toList());

        listaLancamentos.sort((l1, l2) -> {
            Date dataL1 = l1.data();
            Date dataL2 = l2.data();

            // Comparar as datas de forma decrescente
            return dataL2.compareTo(dataL1);
        });

        System.out.println("Depois Lista Lancamentos: ");
        for(ListaLancamentosDTO l : listaLancamentos) {
            System.out.println("Data: " + l.data());
            for(LancamentosDTO la: l.lancamentos()) {
                System.out.println("Id: " + la.id() + " dataDaReceitaVencimento: " + la.dataDaReceitaVencimento() + " dataRecebimentoPagamento: " + la.dataRecebimentoPagamento() + " Status: " + la.status() + " Descricao: " + la.descricao() + " Categoria: " + la.categoriaNome() + " Valor: " + la.valor() + " Tipo: " + la.tipoReceita());
            }
        }

        System.out.println("total vendas: " + totalVendasDiarias);
        System.out.println("Total vendas ate data: " + totalVendasDiariasAteData);

        totalReceitasPeriodo += somaTotalVendasPeriodo;



        PaginaLancamentosDTO paginaLancamento = new PaginaLancamentosDTO(aReceber,aPagar,projecaoSaldo, saldoAnterior, saldoAtual, listaLancamentos, listasPorcentagemCategorias, new Date(), totalReceitasPeriodo, totalDespesasPeriodo);

        return paginaLancamento;
    }

//    public PaginaLancamentosDTO getLancamentos(String dataInicio, String dataFim) {
//
//        LocalDate localDateInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDate localDateFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        // Variáveis de dia, mês e ano já não são necessárias
//        CategoriasDTO categorias = this.getCategorias();
//
//        double projecaoSaldo = 0;
//        double saldoAtual = 0;
//        double saldoAnterior = 0;
//        double aPagar = 0;
//        double aReceber = 0;
//
//        List<Object[]> somaReceitasDespesas = this.lancamentoRepository.getLancamentoSums(localDateInicio, localDateFim);
//        if (somaReceitasDespesas != null && !somaReceitasDespesas.isEmpty()) {
//            Object[] row = somaReceitasDespesas.get(0);
//            if (row[0] != null && row[1] != null && row[2] != null && row[3] != null && row[4] != null
//                    && row[5] != null && row[6] != null && row[7] != null && row[8] != null && row[9] != null) {
//                Double totalDespesas = ((Number) row[0]).doubleValue();
//                Double totalReceitas = ((Number) row[1]).doubleValue();
//                Double totalDespesasPeriodo = ((Number) row[2]).doubleValue();
//                Double totalReceitasPeriodo = ((Number) row[3]).doubleValue();
//                Double totalDespesasPeriodoAnterior = ((Number) row[4]).doubleValue();
//                Double totalReceitasPeriodoAnterior = ((Number) row[5]).doubleValue();
//                Double totalDespesasSaldoAtual = ((Number) row[6]).doubleValue();
//                Double totalReceitasSaldoAtual = ((Number) row[7]).doubleValue();
//                Double totalDespesasAPagar = ((Number) row[8]).doubleValue();
//                Double totalReceitasSAReceber = ((Number) row[9]).doubleValue();
//
//                projecaoSaldo = totalReceitas - totalDespesas;
//                saldoAtual = totalReceitasSaldoAtual - totalDespesasSaldoAtual;
//                saldoAnterior = totalReceitasPeriodoAnterior - totalDespesasPeriodoAnterior;
//                aPagar = totalDespesasAPagar;
//                aReceber = totalReceitasSAReceber;
//            }
//        }
//
//        List<LancamentosDTO> lancamentos = this.lancamentoRepository
//                .getLancamentosPorData(localDateInicio.getDayOfMonth(), localDateInicio.getMonthValue(), localDateInicio.getYear(),
//                        localDateFim.getDayOfMonth(), localDateFim.getMonthValue(), localDateFim.getYear())
//                .stream()
//                .map(l -> new LancamentosDTO(
//                        l.getId(),
//                        l.getDataDaReceitaVencimento(),
//                        l.getDataRecebimentoPagamento(),
//                        getStatus(l),
//                        l.getDescricao(),
//                        getNomeCategoria(l.getCategoriaId(), l.getTipoReceita(), categorias),
//                        l.getValor(),
//                        l.getTipoReceita()
//                ))
//                .toList();
//
//        List<LancamentosDTO> lancamentosMutavel = new ArrayList<>(lancamentos);
//
//        lancamentosMutavel.sort((l1, l2) -> {
//            Date dataL1 = l1.dataRecebimentoPagamento() != null ? l1.dataRecebimentoPagamento() : l1.dataDaReceitaVencimento();
//            Date dataL2 = l2.dataRecebimentoPagamento() != null ? l2.dataRecebimentoPagamento() : l2.dataDaReceitaVencimento();
//            return dataL2.compareTo(dataL1);
//        });
//
//        List<ListaLancamentosDTO> listaLancamentos = new ArrayList<>();
//        Map<String, ListaLancamentosDTO> agrupamentoPorData = new LinkedHashMap<>();
//        ListasPorcentagemCategoriasDTO listasPorcentagemCategorias = new ListasPorcentagemCategoriasDTO(null, null);
//
//        Map<String, Integer> contagemPorCategoriaReceita = new HashMap<>();
//        Map<String, Integer> contagemPorCategoriaDespesa = new HashMap<>();
//
//        int qtdLancReceita = 0;
//        int qtdLancDespesa = 0;
//        for (LancamentosDTO lancamento : lancamentosMutavel) {
//            String categoria = lancamento.categoriaNome();
//            if (lancamento.tipoReceita().equals("receita")) {
//                contagemPorCategoriaReceita.put(categoria, contagemPorCategoriaReceita.getOrDefault(categoria, 0) + 1);
//                qtdLancReceita++;
//            } else if (lancamento.tipoReceita().equals("despesa")) {
//                contagemPorCategoriaDespesa.put(categoria, contagemPorCategoriaDespesa.getOrDefault(categoria, 0) + 1);
//                qtdLancDespesa++;
//            }
//
//            Date dataAgrupamento = lancamento.dataRecebimentoPagamento() != null ? lancamento.dataRecebimentoPagamento() : lancamento.dataDaReceitaVencimento();
//            String chaveData = String.format("%d-%02d-%02d", dataAgrupamento.getYear(), dataAgrupamento.getMonth(), dataAgrupamento.getDate());
//
//            if (!agrupamentoPorData.containsKey(chaveData)) {
//                agrupamentoPorData.put(chaveData, new ListaLancamentosDTO(dataAgrupamento, new ArrayList<>()));
//            }
//            agrupamentoPorData.get(chaveData).lancamentos().add(lancamento);
//        }
//
//        List<CategoriaPorcentagemDTO> listaPorcentagemReceitas = new ArrayList<>();
//        List<CategoriaPorcentagemDTO> listaPorcentagemDespesas = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : contagemPorCategoriaReceita.entrySet()) {
//            String categoria = entry.getKey();
//            int quantidade = entry.getValue();
//            double porcentagem = (quantidade / (double) qtdLancReceita) * 100;
//            listaPorcentagemReceitas.add(new CategoriaPorcentagemDTO(categoria, porcentagem));
//        }
//        for (Map.Entry<String, Integer> entry : contagemPorCategoriaDespesa.entrySet()) {
//            String categoria = entry.getKey();
//            int quantidade = entry.getValue();
//            double porcentagem = (quantidade / (double) qtdLancDespesa) * 100;
//            listaPorcentagemDespesas.add(new CategoriaPorcentagemDTO(categoria, porcentagem));
//        }
//
//        listasPorcentagemCategorias.setReceitas(listaPorcentagemReceitas);
//        listasPorcentagemCategorias.setDespesas(listaPorcentagemDespesas);
//
//        listaLancamentos.addAll(agrupamentoPorData.values());
//
//        // Adicionar uma venda para cada dia no intervalo de datas
//        LocalDate currentDay = localDateInicio;
//        while (!currentDay.isAfter(localDateFim)) {
//            Double total = this.vendaRepository.totalVendasLancamento(currentDay.getDayOfMonth(), currentDay.getMonthValue(), currentDay.getYear(),
//                    currentDay.getDayOfMonth(), currentDay.getMonthValue(), currentDay.getYear());
//
//            if (total == null) {
//                total = 0d;
//                currentDay = currentDay.plusDays(1);
//                continue;
//            }
//
//            LancamentosDTO vendasDoDia = new LancamentosDTO(0L, Date.from(currentDay.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//                    Date.from(currentDay.atStartOfDay(ZoneId.systemDefault()).toInstant()), "Pago", "Vendas diárias", "Vendas diárias", total, "receita");
//
//            ListaLancamentosDTO vendas = new ListaLancamentosDTO(Date.from(currentDay.atStartOfDay(ZoneId.systemDefault()).toInstant()), new ArrayList<>());
//            vendas.lancamentos().add(vendasDoDia);
//
//            listaLancamentos.add(0, vendas);
//
//            currentDay = currentDay.plusDays(1);
//        }
//
//        PaginaLancamentosDTO paginaLancamento = new PaginaLancamentosDTO(aReceber, aPagar, projecaoSaldo, saldoAnterior, saldoAtual, listaLancamentos, listasPorcentagemCategorias, new Date());
//
//        return paginaLancamento;
//    }



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

    public CadastroLancamentoDTO findLancamento(long id) {
        Lancamento l = new Lancamento();
        Optional<Lancamento> lancOpt = this.lancamentoRepository.findById(id);
        if (lancOpt.isPresent())
            l = lancOpt.get();
        return new CadastroLancamentoDTO(l);
    }
}
