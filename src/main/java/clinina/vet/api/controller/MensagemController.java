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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository repository;

    @GetMapping
    public List<MensagemDTO> listarMensagens(@RequestParam String estado){
        List<Mensagem> mensagens;
        switch (estado.toLowerCase()) {
            case "lido":
                mensagens = this.repository.findByExcluirAndLeitura(0, 1);
                break;
            case "naolido":
                mensagens = this.repository.findByExcluirAndLeitura(0, 0);
                break;
            case "todos":
            default:
                mensagens = this.repository.findByExcluir(0);
        }
        return mensagens.stream().map(MensagemDTO::new).toList();
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

    @PatchMapping("/editarMensagemVisto/{id}")
    @Transactional
    public void editarMensagemVisto(@PathVariable Long id, @RequestBody MensagemDTO dto) {
        Optional<Mensagem> msg = repository.findById(id);
        if (msg.isPresent()) {
            System.out.println("autor editar: " + msg.get().getAutor());
            msg.get().setLeitura(dto.leitura());
            msg.get().setModified_at(dto.modified_at());
        }
    }

    @PatchMapping("/apagarMensagem/{id}")
    @Transactional
    public void apagarMensagem(@PathVariable Long id, @RequestBody MensagemDTO dto) {
        Optional<Mensagem> msg = repository.findById(id);
        if (msg.isPresent()) {
            System.out.println("autor apagar: " + msg.get().getAutor());
            msg.get().setExcluir(dto.excluir());
            msg.get().setModified_at(dto.modified_at());
        }
    }

}
