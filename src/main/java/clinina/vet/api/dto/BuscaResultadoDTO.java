package clinina.vet.api.dto;

import java.util.List;

public record BuscaResultadoDTO(
        List<TutorComAnimaisDTO> tutoresLista,
        List<AnimalDetalhadoDTO> animaisLista
) {
    public BuscaResultadoDTO(List<TutorComAnimaisDTO> tutoresLista, List<AnimalDetalhadoDTO> animaisLista) {
        this.tutoresLista = tutoresLista;
        this.animaisLista = animaisLista;
    }
}
