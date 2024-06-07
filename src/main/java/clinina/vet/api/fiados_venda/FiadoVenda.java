package clinina.vet.api.fiados_venda;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "fiado_vendas")
@Entity(name = "FiadoVenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FiadoVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produto_id;

    private int quantidade;
    private Double precoUnitario;
    private String peso;
    private Date data;
    private Long idvendafiado;
    private Double desconto;

    @Transient
    private String nome;
    @Transient
    private String telefone;
    @Transient
    private String endereco;
}
