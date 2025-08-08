package clinina.vet.api.controller;

import clinina.vet.api.dto.AnimalDTO;
import clinina.vet.api.dto.BuscaResultadoDTO;
import clinina.vet.api.mapper.AnimalMapper;
import clinina.vet.api.model.Animal;
import clinina.vet.api.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banho-e-tosa/animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private AnimalMapper animalMapper;

    @PostMapping
    public AnimalDTO criar(@RequestBody AnimalDTO dto) {
        Animal animal = animalMapper.toEntity(dto);
        Animal salvo = animalService.salvar(animal);
        return animalMapper.toDTO(salvo);
    }

    @GetMapping
    public List<AnimalDTO> listar() {
        return animalService.listar().stream().map(animalMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public AnimalDTO buscarPorId(@PathVariable Long id) {
        Animal animal = animalService.buscarPorId(id);
        return animalMapper.toDTO(animal);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        animalService.excluir(id);
    }

    @PutMapping("/{id}")
    public AnimalDTO atualizar(@PathVariable Long id, @RequestBody AnimalDTO dto) {
        Animal atualizado = animalService.atualizar(id, dto);
        return animalMapper.toDTO(atualizado);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<BuscaResultadoDTO> pesquisar(@RequestParam("q") String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return ResponseEntity.ok(new BuscaResultadoDTO(List.of(), List.of()));
        }
        return ResponseEntity.ok(animalService.buscarAnimaisOuTutores(termo.trim()));
    }
}
