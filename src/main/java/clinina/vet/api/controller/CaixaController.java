package clinina.vet.api.controller;

import clinina.vet.api.caixa.Caixa;
import clinina.vet.api.caixa.CaixaCompletoDTO;
import clinina.vet.api.caixa.CaixaDTO;
import clinina.vet.api.caixa.CaixaRepository;
import clinina.vet.api.venda.DadosItensVendidos;
import clinina.vet.api.venda.Venda;
import clinina.vet.api.venda.VendaRepository;
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

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping("/buscarCaixa")
    public CaixaCompletoDTO buscarCaixa(@RequestParam int dia, @RequestParam int mes, @RequestParam int ano){
        Caixa caixa = caixaRepository.buscarCaixa(dia, mes, ano);
        CaixaCompletoDTO caixaCompletoDTO = null;
        List<DadosItensVendidos> venda = this.vendaRepository.buscarItensVendidos(dia, mes, ano, dia, mes, ano);
        double creditoTotal = 0d;
        double debitoTotal = 0d;
        double dinheiroTotal = 0d;
        double pixTotal = 0d;
        double fiadoTotal = 0d;
        if (!venda.isEmpty()) {
            for (DadosItensVendidos v : venda) {
                if (v.getPagamento().equals("Crédito")) {
                    creditoTotal += (v.getPrecoUnitario() - v.getDesconto()) * v.getQuantidade();
                }
                else if (v.getPagamento().equals("Débito")) {
                    debitoTotal += (v.getPrecoUnitario() - v.getDesconto()) * v.getQuantidade();
                }
                else if (v.getPagamento().equals("Dinheiro")) {
                    dinheiroTotal += (v.getPrecoUnitario() - v.getDesconto()) * v.getQuantidade();
                }
                else if (v.getPagamento().equals("Pix")) {
                    pixTotal += (v.getPrecoUnitario() - v.getDesconto()) * v.getQuantidade();
                }
                else if (v.getPagamento().equals("Fiado")) {
                    fiadoTotal += (v.getPrecoUnitario() - v.getDesconto()) * v.getQuantidade();
                }
            }
        }
        if (caixa != null) {
            caixaCompletoDTO = new CaixaCompletoDTO(caixa, creditoTotal, debitoTotal, dinheiroTotal, pixTotal, fiadoTotal);
        }

        return caixaCompletoDTO;
    }

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