package clinina.vet.api.tutor;

import clinina.vet.api.animais.Animais;
import clinina.vet.api.animais.AnimaisRepository;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.endereco.EnderecoRepository;
import clinina.vet.api.servicos.Servicos;
import clinina.vet.api.servicos.ServicosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TutorService2 {

    @Autowired
    private TutorRepository2 tutorRepository2;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private AnimaisRepository animaisRepository;

    @Transactional
    public TutorDTO saveTutor(TutorDTO dto) {
        Tutor tutor = new Tutor(dto);
        //Salvar endereço
        Endereco savedEndereco = enderecoRepository.save(dto.endereco());
        tutor.setEndereco(savedEndereco);

        //Salvar tutor
        Tutor savedTutor = tutorRepository2.save(tutor);

        //Salvar servicos
        if (tutor.getServicos() != null) {
            for (Servicos servico: tutor.getServicos()) {
                servico.setTutor(savedTutor);
                servicosRepository.save(servico);
            }
        }

        //Salvar animais
        if (tutor.getAnimais() != null) {
            for (Animais animal : tutor.getAnimais()) {
                animal.setTutor(savedTutor);
                animaisRepository.save(animal);
            }
        }
        TutorDTO tutorDTO = new TutorDTO(savedTutor);
        return tutorDTO;
    }

    public List<TutorDTO> listarTutores() {
        return tutorRepository2.findAll().stream().map(TutorDTO:: new).collect(Collectors.toList());
    }

    @Transactional
    public TutorDTO updateTutor(Long id, AlterarTutorDTO dto) {
        Optional<Tutor> existingTutor = tutorRepository2.findById(id);
        if (existingTutor.isEmpty()) {
            return null;
        }
        Tutor tutor = existingTutor.get();

        //Atualizar Tutor
        if (dto.nome() != null) tutor.setNome(dto.nome());
        if (dto.cpf() != null) tutor.setCpf(dto.cpf());
        if (dto.email() != null) tutor.setEmail(dto.email());
        if (dto.telefone() != null) tutor.setTelefone(dto.telefone());
        if (dto.celular() != null) tutor.setCelular(dto.celular());

        if (dto.endereco() != null) {
            Endereco endereco = enderecoRepository.findById(dto.endereco().getId()).orElse(new Endereco());
            if (dto.endereco().getRua() != null)
                endereco.setRua(dto.endereco().getRua());
            if (dto.endereco().getNumero() != null)
                endereco.setNumero(dto.endereco().getNumero());
            if (dto.endereco().getCidade() != null)
                endereco.setCidade(dto.endereco().getCidade());
            if (dto.endereco().getUf() != null)
                endereco.setUf(dto.endereco().getUf());
            if (dto.endereco().getCep() != null)
                endereco.setCep(dto.endereco().getCep());
            if (dto.endereco().getComplemento() != null)
                endereco.setComplemento(dto.endereco().getComplemento());
            if (dto.endereco().getBairro() != null)
                endereco.setBairro(dto.endereco().getBairro());
            endereco = enderecoRepository.save(endereco);
            tutor.setEndereco(endereco);
        }
        if (dto.animais() != null) {
            for (Animais animalDTO : dto.animais()) {
                Animais animal = new Animais();
                if (animalDTO.getId() != null) {
                    animal = animaisRepository.findById(animalDTO.getId()).orElse(new Animais());
                }
                if (animalDTO.getNome() != null)
                    animal.setNome(animalDTO.getNome());
                if (animalDTO.getRaca() != null)
                    animal.setRaca(animalDTO.getRaca());
                if (animalDTO.getCor() != null)
                    animal.setCor(animalDTO.getCor());
                if (animalDTO.getPelagem() != null)
                    animal.setPelagem(animalDTO.getPelagem());
                if (animalDTO.getDataDeNascimento() != null)
                    animal.setDataDeNascimento(animalDTO.getDataDeNascimento());
                if (animalDTO.getPeso() != null)
                    animal.setPeso(animalDTO.getPeso());
                if (animalDTO.getObs() != null)
                    animal.setObs(animalDTO.getObs());
                animal.setTutor(tutor);
                animaisRepository.save(animal);
            }
        }

        Tutor updatedTutor = tutorRepository2.save(tutor);

        return new TutorDTO(updatedTutor);
    }

    public TutorDTO getTutorById(Long id) {
        Optional<Tutor> tutor = tutorRepository2.findById(id);
        if (tutor.isEmpty()) {
            return null;
        }
        return new TutorDTO(tutor.get());
    }
}
