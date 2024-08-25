package clinina.vet.api.funcionario;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "funcionarios")
@Entity(name = "Funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String senha;
    private String grupo;
}

