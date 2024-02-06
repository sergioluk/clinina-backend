package clinina.vet.api.categoria;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public void salvarCategoria(DadosCadastroCategoria dados) {
        Categoria categoria = new Categoria();
        categoria.setNome(dados.nome());
        categoriaRepository.save(categoria);
    }
}
