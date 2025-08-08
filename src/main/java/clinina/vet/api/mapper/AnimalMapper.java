package clinina.vet.api.mapper;

import clinina.vet.api.dto.AnimalDTO;
import clinina.vet.api.model.Animal;
import clinina.vet.api.model.Tutor;
import clinina.vet.api.model.enums.Especie;
import clinina.vet.api.model.enums.Sexo;
import clinina.vet.api.model.enums.TamanhoAnimal;
import clinina.vet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    @Autowired
    private TutorRepository tutorRepository;

    public AnimalDTO toDTO(Animal animal) {
        return new AnimalDTO(
                animal.getId(),
                animal.getNome(),
                animal.getEspecie().name(),
                animal.getSexo().name(),
                animal.getRaca(),
                animal.getCor(),
                animal.getPelagem(),
                animal.getDataNascimento(),
                animal.getTamanho().name(),
                animal.isCastrado(),
                animal.getObservacoes(),
                animal.getFoto(),
                animal.getTutor().getId(),
                animal.getTutor().getPessoa().getNome()
        );
    }

    public Animal toEntity(AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setId(dto.id());
        animal.setNome(dto.nome());
        animal.setEspecie(Especie.valueOf(dto.especie()));
        animal.setSexo(Sexo.valueOf(dto.sexo()));
        animal.setRaca(dto.raca());
        animal.setCor(dto.cor());
        animal.setPelagem(dto.pelagem());
        animal.setDataNascimento(dto.dataNascimento());
        animal.setTamanho(TamanhoAnimal.valueOf(dto.tamanho()));
        animal.setCastrado(dto.castrado());
        animal.setObservacoes(dto.observacoes());
        animal.setFoto(dto.foto());

        Tutor tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado com ID: " + dto.tutorId()));
        animal.setTutor(tutor);

        return animal;
    }
}
