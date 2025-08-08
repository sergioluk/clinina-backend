package clinina.vet.api.model;

import clinina.vet.api.model.enums.StatusAgendamento;
import clinina.vet.api.model.enums.TipoAgendamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "agendamento")
@Entity(name = "Agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;


    @Column(nullable = false)
    private LocalDate data;
    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;
    @Column(name = "hora_saida")
    private LocalTime horaSaida;


    @Enumerated(EnumType.STRING)
    private TipoAgendamento tipo;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    private String descricao;
    private String observacoes;

    @ManyToMany
    @JoinTable(
            name = "agendamento_servico",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos = new ArrayList<>();
}
