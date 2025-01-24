package clinina.vet.api.vencimentos;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "vencimentos")
@Entity(name = "Vencimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vencimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_produto")
    private Long idProduto;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "visto")
    private Integer visto = 0;
}
