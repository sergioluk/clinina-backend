package clinina.vet.api.controller;

import clinina.vet.api.animais.Animais;
import clinina.vet.api.tutor.AlterarTutorDTO;
import clinina.vet.api.tutor.Tutor;
import clinina.vet.api.tutor.TutorDTO;
import clinina.vet.api.tutor.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("banho-e-tosa/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    public ResponseEntity<TutorDTO> createTutor(@RequestBody TutorDTO dto) {
        TutorDTO tutor = tutorService.saveTutor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tutor);
    }

    @GetMapping
    public ResponseEntity<List<TutorDTO>> listTutores() {
        return ResponseEntity.ok(tutorService.listarTutores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorDTO> updateTutor(@PathVariable Long id, @RequestBody AlterarTutorDTO dto) {
        System.out.println("Objetp DTO");
        System.out.println("id path: " + id);
        System.out.println("id " + dto.id());
        System.out.println("nome: " + dto.nome());
        System.out.println("email: " + dto.email());
        System.out.println("telefone: " + dto.telefone());
        System.out.println("endereco: " + dto.endereco());
        System.out.println("id endereco: " + dto.endereco().getId());
        System.out.println("cep: " + dto.endereco().getCep());
        System.out.println("uf: " + dto.endereco().getUf());
        System.out.println("cidade: " + dto.endereco().getCidade());
        System.out.println("bairro: " + dto.endereco().getBairro());
        System.out.println("rua: " + dto.endereco().getRua());
        System.out.println("numero: " + dto.endereco().getNumero());
        System.out.println("complemento: " + dto.endereco().getComplemento());
        System.out.println("animal: " + dto.animais());
        for (Animais a : dto.animais()) {
            System.out.println("animal id: " + a.getId());
            System.out.println("nome: " + a.getNome());
            System.out.println("raca: " + a.getRaca());
            System.out.println("cor: " + a.getCor());
            System.out.println("pelagem: " + a.getPelagem());
            System.out.println("dataNascimento: " + a.getDataDeNascimento());
            System.out.println("peso: " + a.getPeso());
            System.out.println("obs: " + a.getObs());
            System.out.println("tutor: " + a.getTutor().getNome());
            System.out.println("tutor id: " + a.getTutor().getId());
        }

        TutorDTO updatedTutor = tutorService.updateTutor(id,dto);

        if (updatedTutor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updatedTutor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> findTutorById(@PathVariable Long id) {
        TutorDTO tutor = tutorService.getTutorById(id);

        if (tutor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(tutor);
    }
}
