package clinina.vet.api.controller;

import clinina.vet.api.caixa.Caixa;
import clinina.vet.api.caixa.CaixaDTO;
import clinina.vet.api.caixa.CaixaRepository;
import clinina.vet.api.venda.DadosItensVendidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("caixa")
public class CaixaController {

    @Autowired
    private CaixaRepository caixaRepository;

    @GetMapping("/buscarCaixa")
    public CaixaDTO buscarCaixa(@RequestParam int dia, @RequestParam int mes, @RequestParam int ano){
        Caixa caixa = caixaRepository.buscarCaixa(dia, mes, ano);
        CaixaDTO caixaDTO = null;
        if (caixa != null) {
            caixaDTO = new CaixaDTO(caixa);
        }
        return caixaDTO;
    }
}
