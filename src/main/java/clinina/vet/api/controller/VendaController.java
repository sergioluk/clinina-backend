package clinina.vet.api.controller;

import clinina.vet.api.fiado.Fiado;
import clinina.vet.api.fiado.FiadoRepository;
import clinina.vet.api.produto.DadosCadastroProduto;
import clinina.vet.api.venda.DadosVenda;
import clinina.vet.api.venda.Venda;
import clinina.vet.api.venda.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("vender")
public class VendaController {

    @Autowired
    private VendaRepository repository;
    @Autowired
    private FiadoRepository fiadoRepository;

    @PostMapping
    @Transactional
    public void cadastrarProduto(@RequestBody List<Venda> dados){
        Long maiorIdDeVenda = fiadoRepository.encontrarMaiorIdVenda();
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
        }


        for (int i = 0; i < dados.size();i++){
            Venda v = new Venda();
            v.setProduto_id(dados.get(i).getProduto_id());
            v.setData(dados.get(i).getData());
            v.setQuantidade(dados.get(i).getQuantidade());
            v.setPrecoUnitario(dados.get(i).getPrecoUnitario());
            v.setPrecoTotal(dados.get(i).getPrecoTotal());
            v.setPeso(dados.get(i).getPeso());
            v.setPagamento(dados.get(i).getPagamento());
            v.setIddevenda(idDeVenda);
            repository.save(v);
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

}
