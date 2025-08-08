package clinina.vet.api.service;

import clinina.vet.api.dto.TutorDTO;
import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.model.Pessoa;
import clinina.vet.api.model.Tutor;
import clinina.vet.api.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    public Tutor buscarPorId(Long id) {
        return tutorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tutor não encontrado com id: " + id)
        );
    }

    public Tutor salvar(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public void deletar(Long id) {
        Tutor tutor = buscarPorId(id);
        tutorRepository.delete(tutor);
        //tutorRepository.deleteById(id);
    }

    @Transactional
    public Tutor atualizar(Long id, TutorDTO dto) {
        Tutor tutorExistente = tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado com id " + id));

        Pessoa pessoa = tutorExistente.getPessoa();

        pessoa.setNome(dto.pessoa().nome());
        pessoa.setCpf(dto.pessoa().cpf());
        pessoa.setTelefone(dto.pessoa().telefone());
        pessoa.setCelular(dto.pessoa().celular());
        pessoa.setEmail(dto.pessoa().email());

        Endereco endereco = pessoa.getEndereco();
        if (endereco == null) {
            endereco = new Endereco();
            pessoa.setEndereco(endereco);
        }

        endereco.setRua(dto.pessoa().endereco().rua());
        endereco.setNumero(dto.pessoa().endereco().numero());
        endereco.setBairro(dto.pessoa().endereco().bairro());
        endereco.setCidade(dto.pessoa().endereco().cidade());
        endereco.setUf(dto.pessoa().endereco().uf());
        endereco.setCep(dto.pessoa().endereco().cep());
        endereco.setComplemento(dto.pessoa().endereco().complemento());

        return tutorRepository.save(tutorExistente);
    }
}
