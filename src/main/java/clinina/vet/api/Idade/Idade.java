package clinina.vet.api.Idade;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "idade")
@Entity(name = "Idade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Idade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
