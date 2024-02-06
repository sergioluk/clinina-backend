package clinina.vet.api.imagens;

import clinina.vet.api.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "imagens")
@Entity(name = "Imagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //private Produto produto;
    @ManyToOne
    @JoinColumn(name = "produto_id")//xxx
    private Produto produto;//xxx

    private String imagem;

}
