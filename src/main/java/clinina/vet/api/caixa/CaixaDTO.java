package clinina.vet.api.caixa;

import clinina.vet.api.mensagem.Mensagem;

import java.util.Date;

public record CaixaDTO(
        Long id,
        Date abertura_data,
        Double abertura_valor,
        Double despesas_caixa,
        Double entrada,
        Date fechamento_caixa_data,
        Double fechamento_caixa_valor,
        Double credito_conferido,
        Double debito_conferido,
        Double dinheiro_conferido,
        Double pix_conferido,
        Double fiado_conferido
) {
    public CaixaDTO(Caixa caixa){
        this(
                caixa.getId(),
                caixa.getAbertura_data(),
                caixa.getAbertura_valor(),
                caixa.getDespesas_caixa(),
                caixa.getEntrada(),
                caixa.getFechamento_caixa_data(),
                caixa.getFechamento_caixa_valor(),
                caixa.getCredito_conferido(),
                caixa.getDebito_conferido(),
                caixa.getDinheiro_conferido(),
                caixa.getPix_conferido(),
                caixa.getFiado_conferido()
        );
    }
}
