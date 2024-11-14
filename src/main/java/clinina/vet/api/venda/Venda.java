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
    private String pagamento;
    private Long iddevenda;
    private Double desconto;

    @Column(name = "id_cliente")
    private Long idCliente;

    //Esse @Transient é para avisar pro spring ignorar esse campo pq não é uma coluna, é só pra usar esses dados mesmo
    @Transient
    private String nome;
    @Transient
    private String telefone;
    @Transient
    private String endereco;

}
