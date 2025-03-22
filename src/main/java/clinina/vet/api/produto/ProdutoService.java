package clinina.vet.api.produto;

import clinina.vet.api.imagens.Imagem;
import clinina.vet.api.imagens.ImagemRepository;
import clinina.vet.api.informacoes.Informacao;
import clinina.vet.api.vencimentos.Vencimento;
import clinina.vet.api.vencimentos.VencimentoService;
import clinina.vet.api.venda.LinhaDoTempoDTO;
import clinina.vet.api.venda.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VencimentoService vencimentoService;

    @Transactional
    public void salvarProduto(DadosCadastroProduto dados) {
        Produto produto = new Produto();
        produto.setCodigoDeBarras(dados.codigoDeBarras());
        produto.setCategoria(dados.categoria());
        produto.setProduto(dados.produto());
        produto.setSabor(dados.sabor());
        produto.setIdade(dados.idade());
        produto.setPreco(dados.preco());
        produto.setPrecoCompra(dados.precoCompra());
        produto.setPeso(dados.peso());
        produto.setDesconto(dados.desconto());
        produto.setAnimal(dados.animal());
        produto.setCastrado(dados.castrado());
        produto.setFornecedor(dados.fornecedor());
        produto.setEstoque(dados.estoque());
        produto.setImagemP(dados.imagemP());

        List<String> imagens = dados.imagens();
        for (String imagem : imagens) {
            Imagem imagemEntity = new Imagem();
            imagemEntity.setImagem(imagem);
            imagemEntity.setProduto(produto);
            produto.getImagens().add(imagemEntity);
        }

        List<String> informacoes = dados.informacao();
        for (String info : informacoes) {
            Informacao i = new Informacao();
            i.setInformacao(info);
            i.setProduto(produto);
            produto.getInformacao().add(i);
        }

        List<String> portes = dados.porte();
        String porteTemp = "";
        for (int i = 0; i < portes.size();i++) {
            String p = portes.get(i);
            if ((i + 1) == portes.size()) { //quando chegar no ultimo da lista, não colocar o /
                porteTemp += p;
            } else {
                porteTemp += p + "/";
            }
        }
        produto.setPorte(porteTemp);



        Produto produtoSalvo = produtoRepository.save(produto);

        Vencimento vencimento = new Vencimento();
        vencimento.setIdProduto(produtoSalvo.getId());
        vencimento.setDataVencimento(dados.dataVencimento());
        vencimento.setDataFabricacao(dados.dataFabricacao());
        Vencimento vencimentoSalvo = this.vencimentoService.salvar(vencimento);
        System.out.println("Vencimento salvo id: " + vencimentoSalvo.getId());
    }

    public List<LinhaDoTempoDTO> linhaDoTempoTodos(Long produtoId) {
        return this.vendaRepository.listaLinhaDoTempoTodos(produtoId).stream().map(LinhaDoTempoDTO::new).toList();
    }

    public List<LinhaDoTempoDTO> linhaDoTempoComParametros(Long produtoId, int dias) {
//        LocalDate hoje = LocalDate.now();
//        LocalDate diaCalculado = hoje.minusDays(dias - 1);
//        System.out.println("Dia Hoje: " + hoje.getDayOfMonth());
//        System.out.println("Mes Hoje: " + hoje.getMonthValue());
//        System.out.println("Ano Hoje: " + hoje.getYear());
        return this.vendaRepository.listaLinhaDoTempoComParam(produtoId,dias).stream().map(LinhaDoTempoDTO::new).toList();
    }

    public List<ProdutoEstoqueDTO> listarProdutosComFiltros(
            boolean codigoDeBarras, boolean produto, boolean categoria, boolean imagens,
            boolean sabor, boolean idade, boolean preco, boolean peso, boolean desconto,
            boolean animal, boolean castrado, boolean porte, boolean precoCompra,
            boolean fornecedor, boolean estoque, boolean imagemP, boolean dataVencimento
    ) {
        List<Object[]> resultados = produtoRepository.buscarProdutosSQL(
                codigoDeBarras ? 1 : 0, produto ? 1 : 0, categoria ? 1 : 0, imagens ? 1 : 0,
                sabor ? 1 : 0, idade ? 1 : 0, preco ? 1 : 0, peso ? 1 : 0, desconto ? 1 : 0,
                animal ? 1 : 0, castrado ? 1 : 0, porte ? 1 : 0, precoCompra ? 1 : 0,
                fornecedor ? 1 : 0, estoque ? 1 : 0, imagemP ? 1 : 0, dataVencimento ? 1 : 0
        );

        return resultados.stream().map(obj -> new ProdutoEstoqueDTO(
                (Long) obj[0],                           // id
                (String) obj[1],                         // codigoDeBarras
                (String) obj[2],                         // produto (deve ser renomeado para nome no DTO)
                String.valueOf(obj[3]),                  // categoria (correção do erro de Long para String)
                Collections.singletonList((String) obj[4]), // url imagem
                (String) obj[5],                         // sabor
                (String) obj[6],                         // idade
                (obj[7] != null) ? ((BigDecimal) obj[7]).doubleValue() : 0.0,  // preco (corrigido de Double para BigDecimal)
                (String) obj[8],                         // peso
                (obj[9] != null) ? ((BigDecimal) obj[9]).doubleValue() : 0.0,  // desconto (corrigido)
                (String) obj[10],                        // animal
                (obj[11] != null) ? (Integer) obj[11] : 0, // castrado
                (String) obj[12],                        // porte
                (obj[13] != null) ? ((BigDecimal) obj[13]).doubleValue() : 0.0, // precoCompra (corrigido)
                (String) obj[14],                        // fornecedor
                (obj[15] != null) ? ((Number) obj[15]).intValue() : 0, // estoque
                (String) obj[16],                        // imagemP
                (obj[17] != null) ? ((Timestamp) obj[17]).toLocalDateTime().toLocalDate() : null // dataVencimento corrigido
        )).toList();



    }


}
