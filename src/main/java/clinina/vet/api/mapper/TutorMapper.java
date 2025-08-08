package clinina.vet.api.mapper;

import clinina.vet.api.dto.TutorDTO;
import clinina.vet.api.model.Tutor;
import org.springframework.stereotype.Component;

@Component
public class TutorMapper {

    private final PessoaMapper pessoaMapper;

    public TutorMapper(PessoaMapper pessoaMapper) {
        this.pessoaMapper = pessoaMapper;
    }

    public TutorDTO toDTO(Tutor tutor) {
        return new TutorDTO(
                tutor.getId(),
                pessoaMapper.toDTO(tutor.getPessoa())
        );
    }

    public Tutor toEntity(TutorDTO dto) {
        return new Tutor(
                pessoaMapper.toEntity(dto.pessoa())
        );
    }
}
