package clinina.vet.api.service;

import clinina.vet.api.dto.ServicoDTO;
import clinina.vet.api.model.Servico;
import clinina.vet.api.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));
    }

    public void excluir(Long id) {
        Servico servico = buscarPorId(id);
        servicoRepository.delete(servico);
    }

    @Transactional
    public Servico atualizar(Long id, ServicoDTO dto) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com id: " + id));

        servicoExistente.setNome(dto.nome());
        servicoExistente.setDescricao(dto.descricao());
        servicoExistente.setPreco(dto.preco());
        servicoExistente.setCategoria(dto.categoria());

        return servicoRepository.save(servicoExistente);
    }
}
