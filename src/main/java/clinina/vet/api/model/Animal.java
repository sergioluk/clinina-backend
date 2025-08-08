package clinina.vet.api.model;

import clinina.vet.api.model.enums.Especie;
import clinina.vet.api.model.enums.Sexo;
import clinina.vet.api.model.enums.TamanhoAnimal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "animal")
@Entity(name = "Animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private Especie especie;

    private String raca;
    private String cor;
    private String pelagem;
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private TamanhoAnimal tamanho;

    private boolean castrado;

    private String observacoes;

    private String foto;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}
