package clinina.vet.api.vencimentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VencimentoService {
    @Autowired
    private VencimentoRepository vencimentoRepository;

    public List<Vencimento> listarTodos() {
        return vencimentoRepository.findAll();
    }

    public Optional<Vencimento> buscarPorId(Long id) {
        return vencimentoRepository.findById(id);
    }

    public Vencimento salvar(Vencimento vencimento) {
        return vencimentoRepository.save(vencimento);
    }

    public void deletarPorId(Long id) {
        vencimentoRepository.deleteById(id);
    }

    public Vencimento buscarPorIdProduto(Long idProduto) {
        return vencimentoRepository.findByIdProduto(idProduto)
                .orElseThrow(() -> new RuntimeException("Vencimento não encontrado para o produto: " + idProduto));
    }

}
