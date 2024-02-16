package clinina.vet.api.fiado;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "fiados")
@Entity(name = "Fiado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fiado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String endereco;
    private Date data;

    private Long vendaId;
    private int pagou;

}
