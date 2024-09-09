package clinina.vet.api.controller;

import clinina.vet.api.lancamento.*;
import clinina.vet.api.receita_categoria.ReceitaCategoriaDTO;
import clinina.vet.api.receita_categoria.ReceitaCategoriaRepository;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;
import clinina.vet.api.despesa_categoria.DespesaCategoriaRepository;
import clinina.vet.api.venda.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping("/categorias")
    public CategoriasDTO getCategorias() {
        return this.lancamentoService.getCategorias();
    }

    @PostMapping
    public ResponseEntity<CadastroLancamentoDTO> cadastrar(@RequestBody CadastroLancamentoDTO lancamentoDTO) {
        CadastroLancamentoDTO cadastro = this.lancamentoService.cadastrar(lancamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/lista")
    public List<LancamentosDTO> getLancamentos() {
        return this.lancamentoService.getLancamentos();
    }
}
