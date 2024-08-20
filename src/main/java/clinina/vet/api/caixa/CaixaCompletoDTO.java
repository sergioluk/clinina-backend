package clinina.vet.api.caixa;

import java.util.Date;

public record CaixaCompletoDTO(
        Long id,
        Date abertura_data,
        double abertura_valor,
        double despesas_caixa,
        double entrada,
        Date fechamento_caixa_data,
        double fechamento_caixa_valor,
        double credito_conferido,
        double debito_conferido,
        double dinheiro_conferido,
        double pix_conferido,
        double fiado_conferido,
        String status,
        double totalCredito,
        double totalDebito,
        double totalDinheiro,
        double totalPix,
        double totalFiado
) {
    public CaixaCompletoDTO(Caixa caixa, double credito, double debito, double dinheiro, double pix, double fiado){
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
                caixa.getFiado_conferido(),
                caixa.getStatus(),
                credito,
                debito,
                dinheiro,
                pix,
                fiado
        );
    }
}
