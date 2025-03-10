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

import java.time.LocalDate;
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

}
