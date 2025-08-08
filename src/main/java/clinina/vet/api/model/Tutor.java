package clinina.vet.api.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tutores")
@Entity(name = "Tutor")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL) // <<< ISSO FAZ SALVAR A PESSOA JUNTO
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    public Tutor() {
    }

    public Tutor(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
