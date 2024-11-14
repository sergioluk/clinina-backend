package clinina.vet.api.clientes;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "clientes")
@Entity(name = "Clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;

}
