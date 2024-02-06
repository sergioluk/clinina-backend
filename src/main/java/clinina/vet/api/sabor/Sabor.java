package clinina.vet.api.sabor;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "sabor")
@Entity(name = "Sabor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
