package clinina.vet.api.vencimentos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VencimentoRepository extends JpaRepository<Vencimento, Long> {
    Optional<Vencimento> findByIdProduto(Long idProduto);
}
