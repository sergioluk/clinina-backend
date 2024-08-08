package clinina.vet.api.controller;

import clinina.vet.api.tutor.Tutor;
import clinina.vet.api.tutor.TutorDTO;
import clinina.vet.api.tutor.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("banho-e-tosa/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    public ResponseEntity<TutorDTO> createTutor(@RequestBody TutorDTO dto) {
        Tutor savedTutor = tutorService.saveTutor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
