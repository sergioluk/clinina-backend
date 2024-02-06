package clinina.vet.api.fornecedor;

import clinina.vet.api.Idade.DadosCadastroIdade;
import clinina.vet.api.Idade.Idade;
import clinina.vet.api.Idade.IdadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Transactional
    public void salvarFornecedor(DadosCadastroFornecedor dados) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dados.nome());
        fornecedorRepository.save(fornecedor);
    }

}
