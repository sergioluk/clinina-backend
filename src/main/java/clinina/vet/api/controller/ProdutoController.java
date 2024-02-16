package clinina.vet.api.controller;

import clinina.vet.api.Idade.DadosCadastroIdade;
import clinina.vet.api.fiado.DadoListagemFiado;
import clinina.vet.api.fiado.DadosEditarFiado;
import clinina.vet.api.fiado.Fiado;
import clinina.vet.api.fiado.FiadoRepository;
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
import clinina.vet.api.venda.DadosItensVendidos;
import clinina.vet.api.venda.Venda;
import clinina.vet.api.venda.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private ProdutoService produtoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private IdadeService idadeService;
    @Autowired
    private SaborService saborService;
    @Autowired
    private FornecedorService fornecedorService;

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
            dados = new DadosVendaProduto(produto.getId(),produto.getCodigoDeBarras(), produto.getProduto(), produto.getPreco(),produto.getImagemP());
            return dados;
        }
        return null;
        //return repository.findByCodigoDeBarras(codigoDeBarras);
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

    @GetMapping("/relatorio")
    public List<DadosItensVendidos> pegarListaDeItensVendidos(){
        return vendaRepository.buscarItensVendidos();
    }

    @GetMapping("/relatorio-fiado")
    public List<DadoListagemFiado> pegarListaDeFiados(){
        List<DadoListagemFiado> listaDeFiadosFinal = new ArrayList<>();
        List<Fiado> listaFiados = this.fiadoRepository.findAll().stream().toList();



        for (int i = 0; i < listaFiados.size(); i++) {
            double valorTotal = 0;
            Long iddevenda = listaFiados.get(i).getVendaId();
            List<DadosItensVendidos> listaDeItensComprados = this.vendaRepository.encontrarItensPeloIdDeVenda(iddevenda);
            for (int j = 0; j < listaDeItensComprados.size(); j++){
                valorTotal += listaDeItensComprados.get(j).getPrecoTotal();
            }

            DadoListagemFiado fiado = new DadoListagemFiado(
                    listaFiados.get(i).getId(),
                    listaFiados.get(i).getNome(),
                    listaFiados.get(i).getTelefone(),
                    listaFiados.get(i).getEndereco(),
                    listaFiados.get(i).getData(),
                    valorTotal,
                    listaDeItensComprados,
                    listaFiados.get(i).getPagou()
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
        }
    }


}
