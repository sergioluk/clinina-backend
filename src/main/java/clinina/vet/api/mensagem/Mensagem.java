package clinina.vet.api.mensagem;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Table(name = "mensagens")
@Entity(name = "Mensagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autor;
    private String mensagem;
    private Date created_at;
    private Date modified_at;
    private int excluir;
    private int leitura;

}
