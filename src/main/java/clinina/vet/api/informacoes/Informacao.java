package clinina.vet.api.informacoes;

import clinina.vet.api.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "informacoes")
@Entity(name = "Informacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Informacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private String informacao;

}
