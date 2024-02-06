package clinina.vet.api.controller;

import clinina.vet.api.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados));
    }

    @GetMapping
    public List<DadosListagemMedico> listar(){
        //return repository.findAll(); //retorna uma lista da entidade Medico
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();

    }

    @GetMapping("/paginacao")
    public Page<DadosListagemMedico> listarPaginado(Pageable paginacao){
       // return repository.findAll(paginacao).map(DadosListagemMedico::new); //tirei isso por causa do exlcuir logico, tem que aparecer so os que nao foi excluido
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        //repository.deleteById(id); //apagar mesmo do banco
        var medico = repository.getReferenceById(id);
        medico.excluirLogicamente();
    }


}
