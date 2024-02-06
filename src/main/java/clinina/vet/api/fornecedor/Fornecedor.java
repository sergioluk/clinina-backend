package clinina.vet.api.fornecedor;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "fornecedor")
@Entity(name = "Fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
