package clinina.vet.api.despesa_categoria;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "despesa_categoria")
@Entity(name = "DespesaCategoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DespesaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

}
