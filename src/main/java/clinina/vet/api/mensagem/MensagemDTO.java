package clinina.vet.api.mensagem;

import clinina.vet.api.sabor.Sabor;

import java.util.Date;

public record MensagemDTO(
        Long id,
        String autor,
        String mensagem,
        Date created_at,
        Date modified_at,
        int excluir,
        int leitura
) {
    public MensagemDTO(Mensagem mensagem){
        this(
                mensagem.getId(),
                mensagem.getMensagem(),
                mensagem.getAutor(),
                mensagem.getCreated_at(),
                mensagem.getModified_at(),
                mensagem.getExcluir(),
                mensagem.getLeitura()
        );
    }
}
