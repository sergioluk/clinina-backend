package clinina.vet.api.fiado;

import java.util.Date;

public record DadosEditarFiado(
        Long id,
        int pagou,
        double pagamento,
        Date modified_at
) {
}
