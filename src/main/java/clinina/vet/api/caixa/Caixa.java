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
}
