package clinina.vet.api.controller;

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
        System.out.println("var: " + id);
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
