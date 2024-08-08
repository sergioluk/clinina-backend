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

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private AnimaisRepository animaisRepository;

    @Transactional
    public Tutor saveTutor(TutorDTO dto) {
        Tutor tutor = new Tutor(dto);
        //Salvar endereço
        Endereco savedEndereco = enderecoRepository.save(dto.endereco());
        tutor.setEndereco(savedEndereco);

        //Salvar tutor
        Tutor savedTutor = tutorRepository.save(tutor);

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
        return savedTutor;
    }
}
