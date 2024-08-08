package clinina.vet.api.tutor;

import clinina.vet.api.animais.Animais;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.servicos.Servicos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "tutor")
@Entity(name = "Tutor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String celular;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "tutor")
    @JsonManagedReference
    private List<Servicos> servicos;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Animais> animais;

    public Tutor(TutorDTO dto) {
        this.nome = dto.nome();
        this.cpf = dto.cpf();
        this.telefone = dto.telefone();
        this.celular = dto.celular();
        this.endereco = dto.endereco();
        this.servicos = dto.servicos();
        this.animais = dto.animais();
    }

}
