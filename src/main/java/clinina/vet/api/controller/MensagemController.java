package clinina.vet.api.controller;

import clinina.vet.api.categoria.DadosCadastroCategoria;
import clinina.vet.api.mensagem.Mensagem;
import clinina.vet.api.mensagem.MensagemDTO;
import clinina.vet.api.mensagem.MensagemRepository;
import clinina.vet.api.produto.DadosCadastroProduto;
import clinina.vet.api.produto.Produto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository repository;

    @GetMapping
    public List<MensagemDTO> listarMensagens(){
        return repository.findAll().stream().map(MensagemDTO::new).toList();
    }

    @PostMapping
    @Transactional
    public void salvarMensagem(@RequestBody MensagemDTO dto){
        Mensagem mensagem = new Mensagem();
        mensagem.setMensagem(dto.mensagem());
        mensagem.setLeitura(dto.leitura());
        mensagem.setExcluir(dto.excluir());
        mensagem.setAutor(dto.autor());
        mensagem.setModified_at(dto.modified_at());
        mensagem.setCreated_at(dto.created_at());
        this.repository.save(mensagem);
    }

}
