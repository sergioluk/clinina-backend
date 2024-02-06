package clinina.vet.api.Idade;

import clinina.vet.api.categoria.Categoria;
import clinina.vet.api.categoria.CategoriaRepository;
import clinina.vet.api.categoria.DadosCadastroCategoria;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdadeService {

    @Autowired
    private IdadeRepository idadeRepository;

    @Transactional
    public void salvarIdade(DadosCadastroIdade dados) {
        Idade idade = new Idade();
        idade.setNome(dados.nome());
        idadeRepository.save(idade);
    }

}
