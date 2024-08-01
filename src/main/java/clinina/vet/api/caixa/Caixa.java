package clinina.vet.api.caixa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "caixa")
@Entity(name = "Caixa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date abertura_data;
    private Double abertura_valor;
    private Double despesas_caixa;
    private Double entrada;
    private Date fechamento_caixa_data;
    private Double fechamento_caixa_valor;
    private Double credito_conferido;
    private Double debito_conferido;
    private Double dinheiro_conferido;
    private Double pix_conferido;
    private Double fiado_conferido;

    public Caixa(CaixaDTO c) {
        this.abertura_data = c.abertura_data();
        this.abertura_valor = c.abertura_valor();
        this.despesas_caixa = c.despesas_caixa();
        this.entrada = c.entrada();
        this.fechamento_caixa_data = c.fechamento_caixa_data();
        this.fechamento_caixa_valor = c.fechamento_caixa_valor();
        this.credito_conferido = c.credito_conferido();
        this.debito_conferido = c.debito_conferido();
        this.dinheiro_conferido = c.dinheiro_conferido();
        this.pix_conferido = c.pix_conferido();
        this.fiado_conferido = c.fiado_conferido();
    }
}
