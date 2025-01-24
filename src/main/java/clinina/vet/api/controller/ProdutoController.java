package clinina.vet.api.controller;

import clinina.vet.api.Idade.DadosCadastroIdade;
import clinina.vet.api.fiado.DadoListagemFiado;
import clinina.vet.api.fiado.DadosEditarFiado;
import clinina.vet.api.fiado.Fiado;
import clinina.vet.api.fiado.FiadoRepository;
import clinina.vet.api.fiados_venda.FiadoVenda;
import clinina.vet.api.fiados_venda.FiadoVendaRepository;
import clinina.vet.api.fornecedor.DadosCadastroFornecedor;
import clinina.vet.api.Idade.IdadeRepository;
import clinina.vet.api.Idade.IdadeService;
import clinina.vet.api.categoria.CategoriaRepository;
import clinina.vet.api.categoria.CategoriaService;
import clinina.vet.api.categoria.DadosCadastroCategoria;
import clinina.vet.api.fornecedor.FornecedorRepository;
import clinina.vet.api.fornecedor.FornecedorService;
import clinina.vet.api.produto.*;
import clinina.vet.api.sabor.DadosCadastroSabor;
import clinina.vet.api.sabor.SaborRepository;
import clinina.vet.api.sabor.SaborService;
import clinina.vet.api.vencimentos.Vencimento;
import clinina.vet.api.vencimentos.VencimentoService;
import clinina.vet.api.venda.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private IdadeRepository idadeRepository;
    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private FiadoRepository fiadoRepository;
    @Autowired
    private FiadoVendaRepository fiadoVendaRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private IdadeService idadeService;
    @Autowired
    private SaborService saborService;
    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private VencimentoService vencimentoService;

    /*
    @GetMapping
    public String lala(){
        return "<h1>Entao, you otavio entrando em site alheio? da pra parar cu isso ou mole?</h1>";
    }*/


    @GetMapping
    public List<DadosListagemProduto> listar(){
       // return repository.findById(1l).toString();
        //return repository.findAll().stream().map(DadosListagemProduto::new).toList();
        return null;
    }
/*
    @GetMapping
    public Optional<Produto> listar(){
        return repository.findById(1l);
    }
*/
   /* @GetMapping("/{id}")
    public List<Produto> list(@PathVariable Long id) {
        return this.repository.findById(id).stream().toList();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public void cadastrarProduto(@RequestBody DadosCadastroProduto dados){
    //public void cadastrarProduto(@RequestBody String dados){
        /*
        System.out.println(dados.categoria().getNome());
        System.out.println(dados);
        repository.save(new Produto(dados));
         */
        produtoService.salvarProduto(dados);
    }

    /*
    @GetMapping("/codigo-de-barras/{codigoDeBarras}")
    public ResponseEntity<Produto> findByCodigoDeBarras(@PathVariable String codigoDeBarras) {
        Optional<Produto> produto = Optional.ofNullable(repository.findByCodigoDeBarras(codigoDeBarras));
        if (produto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produto.get());
    }
     */
    @GetMapping("/codigo-de-barras/{codigoDeBarras}")
    public DadosVendaProduto findByCodigoDeBarras(@PathVariable String codigoDeBarras) {
        Produto produto = repository.findByCodigoDeBarras(codigoDeBarras);
        DadosVendaProduto dados;
        if (produto != null) {
            dados = new DadosVendaProduto(produto.getId(),produto.getCodigoDeBarras(), produto.getProduto(), produto.getPreco(),produto.getImagemP(), produto.getPeso(), 1, produto.getDesconto(), produto.getPrecoCompra());
            return dados;
        }
        return null;
        //return repository.findByCodigoDeBarras(codigoDeBarras);
    }
    @PatchMapping("/editarProduto/{codigoDeBarras}")
    @Transactional
    public void alterarProduto(@PathVariable String codigoDeBarras, @RequestBody DadosCadastroProduto dados) {
        Produto produto = repository.findByCodigoDeBarras(codigoDeBarras);
        System.out.println("codigo de barras: " + codigoDeBarras);
        System.out.println("Produto: " + produto.getProduto());
        if (produto != null) {
            produto.setCodigoDeBarras(dados.codigoDeBarras());
            produto.setProduto(dados.produto());
            produto.setImagemP(dados.imagemP());
            produto.setPrecoCompra(dados.precoCompra());
            produto.setPreco(dados.preco());
            produto.setEstoque(dados.estoque());

            Vencimento vencimento = vencimentoService.buscarPorIdProduto(produto.getId());
            if (vencimento != null) {
                vencimento.setDataFabricacao(dados.dataFabricacao());
                vencimento.setDataVencimento(dados.dataVencimento());
                vencimentoService.salvar(vencimento); // Atualiza o vencimento no banco
            }
        }
    }

    @GetMapping("/verificarCodigoDeBarras/{codigoDeBarras}")
    public boolean verificarCodigoDeBarras(@PathVariable String codigoDeBarras) {
        return repository.existsByCodigoDeBarras(codigoDeBarras);
    }

    @GetMapping("/encontrar/{nome}")
    public List<ProdutosDTO> findByName(@PathVariable String nome) {
        //return repository.searchByNomeOrCodigoBarras(nome).stream().map(ProdutosDTO::new).toList();

        List<ProdutosDTO> produtosPorNome = repository.findByProdutoContainingIgnoreCase(nome).stream().map(ProdutosDTO::new).toList();
        List<ProdutosDTO> produtosPorCodigoDeBarras = repository.findByCodigoDeBarrasContainingIgnoreCase(nome).stream().map(ProdutosDTO::new).toList();

        //List<Produto> produtosPorNome = repository.findByProdutoContainingIgnoreCase(nome);
        //List<Produto> produtosPorCodigoDeBarras = repository.findByCodigoDeBarrasContainingIgnoreCase(nome);

        return Stream.concat(produtosPorNome.stream(), produtosPorCodigoDeBarras.stream()).distinct().collect(Collectors.toList());
    }

    @GetMapping("/codigo-de-barras/editar/{codigoDeBarras}")
    @Transactional
    public void editar(@PathVariable String codigoDeBarras) {
        //this.editando(codigoDeBarras);
        Produto produto = repository.findByCodigoDeBarras(codigoDeBarras);
        if (produto != null) {
            int qtdAntiga = produto.getEstoque();
            int qtd = qtdAntiga + 1;
            produto.setEstoque(qtd);
        }
    }
    /*

    @PutMapping
    @Transactional
    public void editando(String codigoDeBarras){
        Produto produto = repository.findByCodigoDeBarras(codigoDeBarras);
        if (produto != null) {
            int qtdAntiga = produto.getEstoque();
            int qtd = qtdAntiga + 1;
            produto.setEstoque(qtd);
        }

    }*/

    @GetMapping("/listaCategoria")
    public List<DadosCadastroCategoria> listarCategoria(){
        return categoriaRepository.findAll().stream().map(DadosCadastroCategoria::new).toList();
    }

    @GetMapping("/listaSabor")
    public List<DadosCadastroSabor> listarSabor(){
        return saborRepository.findAll().stream().map(DadosCadastroSabor::new).toList();
    }

    @GetMapping("/listaIdade")
    public List<DadosCadastroIdade> listarIdade(){
        return idadeRepository.findAll().stream().map(DadosCadastroIdade::new).toList();
    }

    @GetMapping("/listaFornecedor")
    public List<DadosCadastroFornecedor> listarFornecedor(){
        return fornecedorRepository.findAll().stream().map(DadosCadastroFornecedor::new).toList();
    }

    @PostMapping("/adicionarCategoria")
    @Transactional
    public void adicinarCategoria(@RequestBody DadosCadastroCategoria dados){
        categoriaService.salvarCategoria(dados);
    }

    @PostMapping("/adicionarIdade")
    @Transactional
    public void adicinarIdade(@RequestBody DadosCadastroIdade dados){
        idadeService.salvarIdade(dados);
    }

    @PostMapping("/adicionarSabor")
    @Transactional
    public void adicinarSabor(@RequestBody DadosCadastroSabor dados){
        saborService.salvarSabor(dados);
    }

    @PostMapping("/adicionarFornecedor")
    @Transactional
    public void adicinarFornecedor(@RequestBody DadosCadastroFornecedor dados){
        fornecedorService.salvarFornecedor(dados);
    }

//    @GetMapping("/relatorio")
//    public List<DadosItensVendidos> pegarListaDeItensVendidos(@RequestParam int start_dia,
//                                                              @RequestParam int start_mes,
//                                                              @RequestParam int start_ano,
//                                                              @RequestParam int end_dia,
//                                                              @RequestParam int end_mes,
//                                                              @RequestParam int end_ano){
@GetMapping("/relatorio")
public RelatorioDTO pegarListaDeItensVendidos(@RequestParam int start_dia,
                                                          @RequestParam int start_mes,
                                                          @RequestParam int start_ano,
                                                          @RequestParam int end_dia,
                                                          @RequestParam int end_mes,
                                                          @RequestParam int end_ano){
        //Verificar se a segunda data não foi escolhidaa
        if (end_dia == 0 || end_mes == 0 || end_ano == 0) {
            end_dia = start_dia;
            end_mes = start_mes;
            end_ano = start_ano;
        }

        //Parte do Grafico
        List<Object[]> resultados = vendaRepository.totalVendasPorDiaNoMesGrafico(start_mes, start_ano);
        List<GraficoDTO> grafico = resultados.stream()
                .map(obj -> new GraficoDTO((Date) obj[0], ((BigDecimal) obj[1]).doubleValue()))
                .toList();
        //List<GraficoDTO> grafico = this.vendaRepository.totalVendasPorDiaNoMesGrafico(start_mes, start_ano);
        List<Double> listaTotalVendas = new ArrayList<>();
        YearMonth anoMes = YearMonth.of(start_ano, start_mes);
        int diasNoMes = anoMes.lengthOfMonth();
        int graficoIndex = 0;
        for (int i = 1; i <= diasNoMes; i++) {
            if (graficoIndex < grafico.size()) {
                GraficoDTO g = grafico.get(graficoIndex);
                LocalDate localDate = ((java.sql.Date) g.data()).toLocalDate();
                int numeroDia = localDate.getDayOfMonth();

                if (numeroDia == i) {
                    // Se o dia no grafico corresponder ao dia atual, adiciona o valor
                    listaTotalVendas.add(g.total_vendas());
                    graficoIndex++; // Avança para o próximo registro de vendas
                } else {
                    // Não há venda nesse dia
                    listaTotalVendas.add(0d);
                }
            } else {
                // Para os dias restantes sem registros de vendas
                listaTotalVendas.add(0d);
            }
        }
        //Fim parte do Grafico

        //return vendaRepository.buscarItensVendidos(start_dia, start_mes, start_ano, end_dia, end_mes, end_ano);
        return new RelatorioDTO(listaTotalVendas, vendaRepository.buscarItensVendidos(start_dia, start_mes, start_ano, end_dia, end_mes, end_ano));
    }

    @GetMapping("/relatorio-fiado")
    public List<DadoListagemFiado> pegarListaDeFiados(){
        List<DadoListagemFiado> listaDeFiadosFinal = new ArrayList<>();
        List<Fiado> listaFiados = this.fiadoRepository.findAll().stream().toList();

        for (int i = 0; i < listaFiados.size(); i++) {
            double valorTotal = 0;
            Long iddevenda = listaFiados.get(i).getVendaId();
            List<DadosItensVendidos> listaDeItensComprados = this.fiadoVendaRepository.encontrarItensPeloIdDeVenda(iddevenda);
            for (int j = 0; j < listaDeItensComprados.size(); j++){
                valorTotal += listaDeItensComprados.get(j).getPrecoUnitario() * listaDeItensComprados.get(j).getQuantidade();
            }

            DadoListagemFiado fiado = new DadoListagemFiado(
                    listaFiados.get(i).getId(),
                    listaFiados.get(i).getNome(),
                    listaFiados.get(i).getTelefone(),
                    listaFiados.get(i).getEndereco(),
                    listaFiados.get(i).getData(),
                    valorTotal,
                    listaDeItensComprados,
                    listaFiados.get(i).getPagou(),
                    listaFiados.get(i).getModified_at(),
                    listaFiados.get(i).getPagamento()
            );
            listaDeFiadosFinal.add(fiado);
        }
        return listaDeFiadosFinal;
    }

    @PutMapping("/editar-fiado/{id}")
    @Transactional
    public void editarFiado(@PathVariable Long id, @RequestBody DadosEditarFiado dados) {
        Fiado fiado = this.fiadoRepository.getReferenceById(id);
        if (fiado != null) {
            fiado.setPagou(dados.pagou());
            fiado.setPagamento(dados.pagamento());
            fiado.setModified_at(dados.modified_at());
        }
    }

    @GetMapping("/ping")
    public Map<String, String> pingar() {
        System.out.println("Pingou");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pingou");
        return response;
    }

    @PostMapping("/cadastrarFiado")
    @Transactional
    public void cadastrarFiado(@RequestBody List<Venda> dados){
        Long maiorIdDeVenda = fiadoVendaRepository.encontrarMaiorIdVenda();
        Long idDeVenda = 0L;
        if (maiorIdDeVenda == null) {
            idDeVenda = 1L;
        } else {
            idDeVenda = maiorIdDeVenda + 1;
        }

        System.out.println("maior id da venda: " + maiorIdDeVenda);

        for (int i = 0; i < dados.size();i++){
            System.out.println("produto_id: " + dados.get(i).getProduto_id());
            System.out.println("quantidade: " + dados.get(i).getQuantidade());
            System.out.println("preco_unitario: " + dados.get(i).getPrecoUnitario());
            System.out.println("preco_total: " + dados.get(i).getPrecoTotal());
            System.out.println("data: " + dados.get(i).getData());
            System.out.println("peso: " + dados.get(i).getPeso());
            System.out.println("pagamento: " + dados.get(i).getPagamento());

            System.out.println("nome: " + dados.get(i).getNome());
            System.out.println("telefone: " + dados.get(i).getTelefone());
            System.out.println("endereco: " + dados.get(i).getEndereco());
            System.out.println("data: " + dados.get(i).getData());
            System.out.println("Desconto: " + dados.get(i).getDesconto());
        }


        for (int i = 0; i < dados.size();i++){
            FiadoVenda f = new FiadoVenda();
            f.setProduto_id(dados.get(i).getProduto_id());
            f.setData(dados.get(i).getData());
            f.setQuantidade(dados.get(i).getQuantidade());
            f.setPrecoUnitario(dados.get(i).getPrecoUnitario());
            f.setPeso(dados.get(i).getPeso());
            f.setIdvendafiado(idDeVenda);
            f.setDesconto(dados.get(i).getDesconto());
            fiadoVendaRepository.save(f);
            //Parte para diminuir quantidade no estoque
            Produto p = this.repository.getReferenceById(dados.get(i).getProduto_id());
            if (p != null) {
                int estoque = p.getEstoque() - dados.get(i).getQuantidade();
                if (estoque < 0) {
                    estoque = 0;
                }
                p.setEstoque(estoque);
            }
        }
        if (dados.get(0).getPagamento().equals("Fiado")){
            Fiado f = new Fiado();
            f.setNome(dados.get(0).getNome());
            f.setTelefone(dados.get(0).getTelefone());
            f.setEndereco(dados.get(0).getEndereco());
            f.setData(dados.get(0).getData());
            f.setPagou(0);
            f.setVendaId(idDeVenda);
            fiadoRepository.save(f);
        }



    }

    @GetMapping("/detalhes-produto/{codigoDeBarras}")
    public ProdutoDetalhesDTO buscarProdutoDetalhes(@PathVariable String codigoDeBarras) {
        Produto produto = repository.findByCodigoDeBarras(codigoDeBarras);
        ProdutoDetalhesDTO dados;
        if (produto != null) {
            //List<LinhaDoTempoDTO> linhaDoTempo = this.produtoService.linhaDoTempo(produto.getId());
            List<LinhaDoTempoDTO> linhaDoTempo = this.produtoService.linhaDoTempoComParametros(produto.getId(), 30);
            LinhaDoTempoDTO objUltimaVenda = linhaDoTempo.stream().max(Comparator.comparing((LinhaDoTempoDTO::dataVenda))).orElse(null);
            Double ultimaVenda = 0d;
            if (objUltimaVenda != null)
                ultimaVenda = objUltimaVenda.precoVenda() * objUltimaVenda.quantidade();
            dados = new ProdutoDetalhesDTO(produto, linhaDoTempo, ultimaVenda);
            return dados;
        }
        return null;
    }

    @GetMapping("/detalhes-produto/linhaDoTempo")
    public List<LinhaDoTempoDTO> buscarListaLinhaDoTempo(@RequestParam String codigoDeBarras, @RequestParam String valor) {
        Produto produto = repository.findByCodigoDeBarras(codigoDeBarras);
        List<LinhaDoTempoDTO> linhaDoTempo;
        if (produto != null) {
            int dias = 0;
            switch (valor) {
                case "7 dias" -> dias = 7;
                case "15 dias" -> dias = 15;
                case "30 dias" -> dias = 30;
                default -> {
                    return this.produtoService.linhaDoTempoTodos(produto.getId());  //Todos
                }
            }
            linhaDoTempo = this.produtoService.linhaDoTempoComParametros(produto.getId(), dias);
            return linhaDoTempo;
        }
        return null;
    }


}
