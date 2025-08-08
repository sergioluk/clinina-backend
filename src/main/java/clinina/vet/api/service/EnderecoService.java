package clinina.vet.api.service;

import clinina.vet.api.endereco.Endereco;
import clinina.vet.api.endereco.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com id: " + id));
    }

    public void excluir(Long id) {
        Endereco endereco = buscarPorId(id);
        enderecoRepository.delete(endereco);
    }
}
