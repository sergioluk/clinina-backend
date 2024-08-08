package clinina.vet.api.animais;

import clinina.vet.api.tutor.Tutor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "animais")
@Entity(name = "Animais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Animais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String raca;
    private String cor;
    private String pelagem;
    private Date dataDeNascimento;
    private Double peso;
    private String obs;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference
    private Tutor tutor;
}
