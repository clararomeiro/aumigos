package com.vet.controller;

import com.vet.model.*;
import com.vet.service.RegistrosFachada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

   private final RegistrosFachada fachada; // Troque Service por Fachada

    public TutorController(RegistrosFachada fachada) {
        this.fachada = fachada;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Tutor> buscar(@PathVariable String cpf) {
        Tutor tutor = fachada.buscarTutor(cpf);
        if (tutor == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tutor);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> cadastrar(@RequestBody Tutor t) {
        fachada.cadastrarTutor(t);
        return ResponseEntity.ok(new GenericResponse("OK", "Tutor cadastrado com sucesso."));
    }
}
