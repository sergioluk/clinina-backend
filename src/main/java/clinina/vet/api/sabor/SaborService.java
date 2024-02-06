package clinina.vet.api.sabor;

import clinina.vet.api.Idade.DadosCadastroIdade;
import clinina.vet.api.Idade.Idade;
import clinina.vet.api.Idade.IdadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborService {
    @Autowired
    private SaborRepository saborRepository;

    @Transactional
    public void salvarSabor(DadosCadastroSabor dados) {
        Sabor sabor = new Sabor();
        sabor.setNome(dados.nome());
        saborRepository.save(sabor);
    }
}
