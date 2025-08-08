package clinina.vet.api.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "cliente")
@Entity(name = "Cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL) // <<< ISSO FAZ SALVAR A PESSOA JUNTO
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    public Cliente(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
