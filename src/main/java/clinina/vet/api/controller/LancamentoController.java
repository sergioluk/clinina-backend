package clinina.vet.api.controller;

import clinina.vet.api.lancamento.*;
import clinina.vet.api.lancamento.lancamentosdto.LancamentosDTO;
import clinina.vet.api.lancamento.lancamentosdto.ListaLancamentosDTO;
import clinina.vet.api.lancamento.lancamentosdto.PaginaLancamentosDTO;
import clinina.vet.api.produto.Produto;
import clinina.vet.api.receita_categoria.CategoriasDTO;
import jakarta.transaction.Transactional;
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
    public ResponseEntity<List<CadastroLancamentoDTO>> cadastrar(@RequestBody CadastroLancamentoDTO lancamentoDTO) {
        List<CadastroLancamentoDTO> cadastro = this.lancamentoService.cadastrar(lancamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/lista")
    public PaginaLancamentosDTO getLancamentos(@RequestParam String dataInicio, @RequestParam String dataFim) {
        return this.lancamentoService.getLancamentos(dataInicio, dataFim);
    }

    @GetMapping("/selecionar/{id}")
    public CadastroLancamentoDTO editar(@PathVariable Long id) {
        System.out.println("ID DIdididiidid: " + id);
        return this.lancamentoService.findLancamento(id);
    }
}
