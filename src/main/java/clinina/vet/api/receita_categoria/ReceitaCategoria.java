package clinina.vet.api.receita_categoria;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "receita_categoria")
@Entity(name = "ReceitaCategoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReceitaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

}
