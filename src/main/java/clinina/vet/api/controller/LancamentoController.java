package clinina.vet.api.controller;

import clinina.vet.api.lancamento.CadastroLancamentoDTO;
import clinina.vet.api.lancamento.Lancamento;
import clinina.vet.api.lancamento.LancamentoRepository;
import clinina.vet.api.receita_categoria.ReceitaCategoriaDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaRepository;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lancamentos")
public class LancamentoController {

    @Autowired
    private ReceitaCategoriaRepository receitaCategoriaRepository;

    @Autowired
    private DespesaCategoriaRepository despesaCategoriaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @GetMapping("/categorias")
    public CategoriasDTO getCategorias() {

        List<ReceitaCategoriaDTO> receitas = this.receitaCategoriaRepository.findAll().stream()
                .map(receita -> new ReceitaCategoriaDTO(receita.getId(), receita.getNome()))
                .toList();
        List<DespesaCategoriaDTO> despesas = this.despesaCategoriaRepository.findAll().stream()
                .map(despesa -> new DespesaCategoriaDTO(despesa.getId(), despesa.getNome()))
                .toList();

        return new CategoriasDTO(receitas, despesas);
    }

    @PostMapping
    public ResponseEntity<CadastroLancamentoDTO> cadastrar(@RequestBody CadastroLancamentoDTO lancamentoDTO) {
        Lancamento lancamento = new Lancamento(lancamentoDTO);
        Lancamento lancamentoCriado = this.lancamentoRepository.save(lancamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CadastroLancamentoDTO(lancamentoCriado));
    }
}
