package clinina.vet.api.controller;

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

    @PostMapping
    @Transactional
    public void cadastrarProduto(@RequestBody List<Venda> dados){
        for (int i = 0; i < dados.size();i++){
            Venda v = new Venda();
            v.setProduto_id(dados.get(i).getProduto_id());
            v.setData(dados.get(i).getData());
            v.setQuantidade(dados.get(i).getQuantidade());
            v.setPrecoUnitario(dados.get(i).getPrecoUnitario());
            v.setPrecoTotal(dados.get(i).getPrecoTotal());
            v.setPeso(dados.get(i).getPeso());
            repository.save(v);
            /*
            System.out.println("produto id " + dados.get(i).getProduto_id());
            System.out.println("data " + dados.get(i).getData());
            System.out.println("qtd " + dados.get(i).getQuantidade());
            System.out.println("preco total " + dados.get(i).getPrecoTotal());
            System.out.println("preco unitario " + dados.get(i).getPrecoUnitario());
             */
        }

    }

}
