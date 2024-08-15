package clinina.vet.api.controller;

import clinina.vet.api.caixa.Caixa;
import clinina.vet.api.caixa.CaixaDTO;
import clinina.vet.api.caixa.CaixaRepository;
import clinina.vet.api.venda.DadosItensVendidos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //Mudar, não é mais save e criar o abricaxia
    @PutMapping("/fecharCaixa")
    @Transactional
    public void fecharCaixa(@RequestBody CaixaDTO caixaDTO) {
        Optional<Caixa> caixa = this.caixaRepository.findById(caixaDTO.id());
        Caixa caixaEncontrado = new Caixa();
        if (caixa.isPresent()) {
            caixaEncontrado = caixa.get();
        }
        caixaEncontrado.setEntrada(caixaDTO.entrada());
        caixaEncontrado.setFechamento_caixa_data(caixaDTO.fechamento_caixa_data());
        caixaEncontrado.setFechamento_caixa_valor(caixaDTO.fechamento_caixa_valor());
        caixaEncontrado.setCredito_conferido(caixaDTO.credito_conferido());
        caixaEncontrado.setDebito_conferido(caixaDTO.debito_conferido());
        caixaEncontrado.setDinheiro_conferido(caixaDTO.dinheiro_conferido());
        caixaEncontrado.setPix_conferido(caixaDTO.pix_conferido());
        caixaEncontrado.setFiado_conferido(caixaDTO.fiado_conferido());
        caixaEncontrado.setStatus(caixaDTO.status());
    }

    @PostMapping("/abrirCaixa")
    public void abrirCaixa(@RequestBody CaixaDTO caixaDTO) {
        Caixa caixa = new Caixa(caixaDTO);
        this.caixaRepository.save(caixa);
    }

}