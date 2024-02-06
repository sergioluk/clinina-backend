package clinina.vet.api.venda;

import clinina.vet.api.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "vendas")
@Entity(name = "Venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produto_id;

    private int quantidade;
    private Double precoUnitario;
    private Double precoTotal;
    private String peso;
    private Date data;

}
