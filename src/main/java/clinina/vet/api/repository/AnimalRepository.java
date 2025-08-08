package clinina.vet.api.repository;

import clinina.vet.api.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository  extends JpaRepository<Animal, Long> {

    @Query(value = """
    SELECT DISTINCT t.id AS tutorId, p.nome AS nomeTutor, p.cpf, p.celular, p.telefone
    FROM tutores t
    JOIN pessoa p ON t.pessoa_id = p.id
    WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
       OR REPLACE(p.cpf, '.', '') LIKE CONCAT('%', :termo, '%')
       OR REPLACE(p.telefone, '-', '') LIKE CONCAT('%', :termo, '%')
       OR REPLACE(p.celular, '-', '') LIKE CONCAT('%', :termo, '%')
""", nativeQuery = true)
    List<Object[]> buscarTutoresComTermo(@Param("termo") String termo);

    @Query(value = """
    SELECT a.id, a.nome, a.raca, a.data_nascimento, a.foto
    FROM animal a
    WHERE a.tutor_id = :tutorId
""", nativeQuery = true)
    List<Object[]> buscarAnimaisDoTutor(@Param("tutorId") Long tutorId);

    @Query(value = """
    SELECT a.id, a.nome AS nomePet, a.raca, a.pelagem, a.tamanho, a.data_nascimento,
           p.nome AS nomeTutor, a.foto, p.cpf, p.celular, p.telefone
    FROM animal a
    JOIN tutores t ON a.tutor_id = t.id
    JOIN pessoa p ON t.pessoa_id = p.id
    WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
""", nativeQuery = true)
    List<Object[]> buscarAnimaisPorNome(@Param("termo") String termo);
}
