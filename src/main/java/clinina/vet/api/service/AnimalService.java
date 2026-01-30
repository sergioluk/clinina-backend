package clinina.vet.api.service;

import clinina.vet.api.dto.*;
import clinina.vet.api.model.Animal;
import clinina.vet.api.model.Tutor;
import clinina.vet.api.model.enums.Especie;
import clinina.vet.api.model.enums.Sexo;
import clinina.vet.api.model.enums.TamanhoAnimal;
import clinina.vet.api.repository.AnimalRepository;
import clinina.vet.api.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public Animal salvar(Animal animal) {
        //return animalRepository.save(animal);
        Long tutorId = animal.getTutor().getId(); // pega o ID do tutor enviado no JSON

        Tutor tutor = tutorRepository.findById(tutorId)
            .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado com id: " + tutorId));

        animal.setTutor(tutor); // substitui o objeto "não gerenciado" pelo objeto persistido

        return animalRepository.save(animal);
    }

    public List<Animal> listar() {
        return animalRepository.findAll();
    }

    public Animal buscarPorId(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado com id: " + id));
    }

    public void excluir(Long id) {
        Animal animal = buscarPorId(id);
        animalRepository.delete(animal);
    }

    @Transactional
    public Animal atualizar(Long id, AnimalDTO dto) {
        Animal existente = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com id: " + id));

        existente.setNome(dto.nome());
        existente.setEspecie(Especie.valueOf(dto.especie()));
        existente.setSexo(Sexo.valueOf(dto.sexo()));
        existente.setRaca(dto.raca());
        existente.setCor(dto.cor());
        existente.setPelagem(dto.pelagem());
        existente.setDataNascimento(dto.dataNascimento());
        existente.setTamanho(TamanhoAnimal.valueOf(dto.tamanho()));
        existente.setCastrado(dto.castrado());
        existente.setObservacoes(dto.observacoes());
        existente.setFoto(dto.foto());

        //Atualiza o tutor caso tenha sido alterado
        //Tutor tutor = new Tutor();
        //tutor.setId(dto.tutorId());
        //existente.setTutor(tutor);
        Tutor tutor = tutorRepository.findById(dto.tutorId())
            .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado com id: " + dto.tutorId()));
        existente.setTutor(tutor);

        return animalRepository.save(existente);
    }

    public BuscaResultadoDTO buscarAnimaisOuTutores(String termo) {
        List<TutorComAnimaisDTO> tutoresLista = new ArrayList<>();
        List<AnimalDetalhadoDTO> animaisLista = new ArrayList<>();

        var tutoresBrutos = animalRepository.buscarTutoresComTermo(termo);
        for (Object[] t : tutoresBrutos) {
            Long tutorId = (Long) t[0];
            String nome = (String) t[1];
            String cpf = (String) t[2];
            String celular = (String) t[3];
            String telefone = (String) t[4];

            var animaisBrutos = animalRepository.buscarAnimaisDoTutor(tutorId);
            List<AnimalResumoDTO> animais = animaisBrutos.stream().map(a -> new AnimalResumoDTO(
                    (Long) a[0],
                    (String) a[1],
                    (String) a[2],
                    a[3] != null ? ((Date) a[3]).toLocalDate() : null,
                    (String) a[4]
            )).toList();

            tutoresLista.add(new TutorComAnimaisDTO(tutorId, nome, cpf, celular, telefone, animais));
        }

        var animaisBrutos = animalRepository.buscarAnimaisPorNome(termo);
        animaisLista = animaisBrutos.stream().map(a -> new AnimalDetalhadoDTO(
                (Long) a[0],
                (String) a[1],
                (String) a[2],
                (String) a[3],
                (String) a[4],
                (LocalDate) (a[5] != null ? ((Date) a[5]).toLocalDate() : null),
                (String) a[6],
                (String) a[7],
                (String) a[8],
                (String) a[9],
                (String) a[10]
        )).toList();

        return new BuscaResultadoDTO(tutoresLista, animaisLista);
    }
}
